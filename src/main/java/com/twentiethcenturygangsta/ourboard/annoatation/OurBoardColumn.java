package com.twentiethcenturygangsta.ourboard.annoatation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation used at the field parameter label
 * Set the configuration for the field(table column) of the entity
 *
 * @author oereo, junhyeok
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface OurBoardColumn {

    /**
     * Set the description of field(table column)
     *
     * @return  field(table column) description
     *
     */
    String description() default "";
}
