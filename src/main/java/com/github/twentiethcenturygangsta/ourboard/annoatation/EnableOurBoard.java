package com.github.twentiethcenturygangsta.ourboard.annoatation;


import com.github.twentiethcenturygangsta.ourboard.config.ShardConfigurationReference;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ShardConfigurationReference.class)
public @interface EnableOurBoard {

    String[] value() default {};
    String[] basePackages() default {};
}
