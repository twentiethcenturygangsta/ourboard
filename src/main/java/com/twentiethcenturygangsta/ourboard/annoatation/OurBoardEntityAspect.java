package com.twentiethcenturygangsta.ourboard.annoatation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class OurBoardEntityAspect {

    @Before("@annotation(com.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity)")
    public String getGroup(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        OurBoardEntity ourBoardEntity = method.getAnnotation(OurBoardEntity.class);
        return ourBoardEntity.group();
    }


}
