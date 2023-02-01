package com.twentiethcenturygangsta.ourboard.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/css/**",
            "/static/**",
            "/resources/**"
    };

    private static final String[] AUTH_CONFIRM_LIST = {
            "/admin/**",
            "/api/v1/add"
    };

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and()
                .csrf().disable() //csrf
                .exceptionHandling()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_CONFIRM_LIST).authenticated()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .and()
                .build(); // 권한 설정
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
