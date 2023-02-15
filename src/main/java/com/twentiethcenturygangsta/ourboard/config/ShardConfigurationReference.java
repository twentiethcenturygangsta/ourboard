package com.twentiethcenturygangsta.ourboard.config;

import com.twentiethcenturygangsta.ourboard.annoatation.EnableOurBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ComponentScan("com.twentiethcenturygangsta.ourboard")
@RequiredArgsConstructor
public class ShardConfigurationReference {
    private final ApplicationContext appContext;

    @Bean
    public void registerBasePackage() {
        Map<String, Object> beans = appContext.getBeansWithAnnotation(EnableOurBoard.class);
        String basePackage = beans.getClass().getPackageName();
    }
}
