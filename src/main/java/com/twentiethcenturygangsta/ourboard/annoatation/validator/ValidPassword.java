package com.twentiethcenturygangsta.ourboard.annoatation.validator;

import com.twentiethcenturygangsta.ourboard.handler.exception.ExceptionCode;
import com.twentiethcenturygangsta.ourboard.validation.IdValidator;
import com.twentiethcenturygangsta.ourboard.validation.PasswordValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    ExceptionCode exceptionCode ();
}
