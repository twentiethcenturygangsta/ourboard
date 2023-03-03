package com.twentiethcenturygangsta.ourboard.annoatation;

import com.twentiethcenturygangsta.ourboard.entity.GroupType;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation used at the class parameter label
 * configure entities to be exposed on the OurBoardMember dashboard
 *
 * @author oereo
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface OurBoardEntity {

    /**
     * The highest group that can group entities in the dashboard,
     * and can designate the group to which the entity belongs.
     *
     * @return  Name of the group to specify
     *
     */
    String group() default GroupType.Constants.DEFAULT;
    String description() default "";
}
