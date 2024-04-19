pipeline {
    agent any
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }
    environment {
        PORT = 8084
        POSTGRES_USER = 'glassstore_user'
        POSTGRES_PASSWORD = 'MzMAvUz47PYGeGWXXf0gBcT8tual6qq1'
        SECRET_KEY = '9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9'
        REDIS_HOST = 'localhost'
        REDIS_PORT = 6379
        REDIS_PASSWORD = 123456
    }
    stages {

        stage('Build and Test') {
            steps {
                script {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t trinhdinhthanh/glass-store-service:latest .'
                }
            }
        }
    }
}