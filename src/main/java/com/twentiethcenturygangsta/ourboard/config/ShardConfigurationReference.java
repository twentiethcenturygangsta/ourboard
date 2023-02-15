package com.twentiethcenturygangsta.ourboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.twentiethcenturygangsta.ourboard")
@RequiredArgsConstructor
public class ShardConfigurationReference {
    private final ApplicationContext appContext;

}
