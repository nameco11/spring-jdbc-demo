package com.tms.springjdbc.infrastructure.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.tms.springjdbc.presentation.controller..*Controller.*(..))")
    public void controllerMethods() {}

    @Pointcut("execution(* com.tms.springjdbc.application.services..*Service.*(..))")
    public void serviceMethods() {}

    @Pointcut("execution(* com.tms.springjdbc.domain.repository..*Dao.*(..))")
    public void daoMethods() {}

    @Around("controllerMethods() || serviceMethods() || daoMethods()")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - startTime;
        logger.info("Method [{}] took {} ms to execute", joinPoint.getSignature(), elapsedTime);
        return result;
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public void logControllerExceptions(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in method [{}]: {}", joinPoint.getSignature(), ex.getMessage());
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logServiceExceptions(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in method [{}]: {}", joinPoint.getSignature(), ex.getMessage());
    }

    @AfterThrowing(pointcut = "daoMethods()", throwing = "ex")
    public void logDaoExceptions(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in method [{}]: {}", joinPoint.getSignature(), ex.getMessage());
    }
}
