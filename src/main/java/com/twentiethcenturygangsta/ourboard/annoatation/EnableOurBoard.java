package com.twentiethcenturygangsta.ourboard.annoatation;


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
