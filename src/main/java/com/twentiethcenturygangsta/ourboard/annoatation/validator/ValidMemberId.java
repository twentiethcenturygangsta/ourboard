package com.twentiethcenturygangsta.ourboard.annoatation.validator;

import com.twentiethcenturygangsta.ourboard.handler.exception.ExceptionCode;
import com.twentiethcenturygangsta.ourboard.validation.IdValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IdValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMemberId {
    ExceptionCode exceptionCode ();
}
