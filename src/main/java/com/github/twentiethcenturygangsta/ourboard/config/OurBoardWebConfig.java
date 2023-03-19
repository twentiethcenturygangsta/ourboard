package com.github.twentiethcenturygangsta.ourboard.config;

import com.github.twentiethcenturygangsta.ourboard.interceptor.OurBoardAuthInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OurBoardWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OurBoardAuthInterceptor())
                .order(1)
                .excludePathPatterns("/our-board/admin/login")
                .addPathPatterns("/our-board/admin/**");
    }
}