FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /build

COPY pom.xml /build/pom.xml
COPY src /build/src

RUN mvn clean install
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/build/target/dependency

COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app

VOLUME /tmptarget/*.jar
EXPOSE 8081
ENTRYPOINT ["java","-cp","app:app/lib/*", "com.thanhtd.glassstore.Application"]
