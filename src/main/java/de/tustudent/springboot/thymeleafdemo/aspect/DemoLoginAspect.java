package de.tustudent.springboot.thymeleafdemo.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class DemoLoginAspect {
    //@Pointcut("execution(* de.tustudent.springbot.thymeleafdemo.dao.EmployeeRepository.*(..))")
    //public

    // setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    // setup pointcut declarations
    @Pointcut("execution(* de.tustudent.springboot.thymeleafdemo.controller.*.*(..))")
    private void forControllerPackage(){}

    // do the same for service and dao
    @Pointcut("execution(* de.tustudent.springboot.thymeleafdemo.dao.*.*(..))")
    private void forDAOPackage(){}

    @Pointcut("execution(* de.tustudent.springboot.thymeleafdemo.service.*.*(..))")
    private void forServicePackage(){}

    @Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
    private void forAppFlow(){}

    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {
        // display method we are calling
        String methodName = joinPoint.getSignature().toShortString();
        myLogger.info("=====>> in @Before: calling method: " + methodName);

        // display the arguments to the method

        // get the arguments
        Object[] args = joinPoint.getArgs();

        // loop through and display args
        for(Object obj : args) {
            myLogger.info("=====>> arguments: " + obj);
        }
    }

    // add @AfterReturning advice
    @AfterReturning(
            pointcut = "forControllerPackage() || forServicePackage() || forDAOPackage()",
            returning = "result"
    )
    public void afterReturning(JoinPoint theJoinPoint, Object result) {
        // display method we are returning from
        String methodName = theJoinPoint.getSignature().toShortString();
        myLogger.info("=====>> in @AfterReturning: from method: " + methodName);

        // display data returned
        myLogger.info("=====>> result: " + result);

    }


}
