package com.twentiethcenturygangsta.ourboard.config;

import com.twentiethcenturygangsta.ourboard.annoatation.EnableOurBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Slf4j
@Configuration
@ComponentScan("com.twentiethcenturygangsta.ourboard")
@RequiredArgsConstructor
public class ShardConfigurationReference {
    private final ApplicationContext appContext;

    @Bean
    public String registerBasePackage() {
        String basePackage = "";
        Map<String, Object> beans = appContext.getBeansWithAnnotation(EnableOurBoard.class);
        for(String key : beans.keySet()) {
            basePackage = beans.get(key).getClass().getPackageName();
        }
        return basePackage;
    }
}
