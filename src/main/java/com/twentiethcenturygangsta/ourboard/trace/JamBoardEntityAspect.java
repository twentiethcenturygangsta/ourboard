package com.twentiethcenturygangsta.ourboard.trace;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class JamBoardEntityAspect {

    @Before("@annotation(com.twentiethcenturygangsta.ourboard.trace.JamBoardEntity)")
    public String getGroup(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        JamBoardEntity jamBoardEntity = method.getAnnotation(JamBoardEntity.class);
        return jamBoardEntity.group();
    }


}
