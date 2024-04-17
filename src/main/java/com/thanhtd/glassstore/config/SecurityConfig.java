package com.thanhtd.glassstore.config;

import com.thanhtd.glassstore.security.JwtAuthenticationEntryPoint;
import com.thanhtd.glassstore.security.filter.JwtAuthenticationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            "**", "/glass-store/users/**"
    };

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors(CorsConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL).permitAll().anyRequest().authenticated())
                .exceptionHandling(customizer -> {
                    customizer.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                    customizer.accessDeniedHandler(new JwtAccessDeniedHandler());
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private static class JwtAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response,
                           AccessDeniedException e) throws IOException, ServletException {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter()
                    .print(HttpStatus.UNAUTHORIZED.name() + ": " + HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
    }
}
