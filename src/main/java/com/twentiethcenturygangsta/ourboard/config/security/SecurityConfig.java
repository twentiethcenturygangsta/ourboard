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
            "/admin/login",
            "/ourboard-resources/**"
    };

    private static final String[] AUTH_CONFIRM_LIST = {
            "/admin/**",
            "/api/v1/add"
    };

    @Bean
    public SecurityFilterChain ourBoardSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().and()
                .cors().disable()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_CONFIRM_LIST).authenticated()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .defaultSuccessUrl("/admin")
                .and()
                .build(); // 권한 설정
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
