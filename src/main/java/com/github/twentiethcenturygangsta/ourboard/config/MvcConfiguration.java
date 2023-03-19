package com.github.twentiethcenturygangsta.ourboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/ourboard-resources/**")
                .addResourceLocations(
                        "classpath:/static/ourboard-resources/css",
                        "classpath:/static/ourboard-resources/js",
                        "classpath:/static/ourboard-resources/font"
                );
    }
}
