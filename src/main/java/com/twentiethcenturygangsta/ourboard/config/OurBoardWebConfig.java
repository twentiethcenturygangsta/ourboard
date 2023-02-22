package com.twentiethcenturygangsta.ourboard.config;

import com.twentiethcenturygangsta.ourboard.filter.OurBoardAuthInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OurBoardWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OurBoardAuthInterceptor())
                .order(1)
                .addPathPatterns("/our-board/admin");
    }
}