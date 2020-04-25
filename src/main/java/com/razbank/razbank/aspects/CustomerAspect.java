package com.razbank.razbank.aspects;

import com.razbank.razbank.entities.customer.Customer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerAspect {

    private static final Logger logger = LoggerFactory.getLogger(CustomerAspect.class);

    //setup point declarations
    @Pointcut("execution(* com.razbank.razbank.controllers.customer.*.*(..))")
    private void forControllerPackage() {}

    // do the same for service and dao
    @Pointcut("execution(* com.razbank.razbank.services.customer.*.*(..))")
    private void forServicePackage() {}

    @Pointcut("execution(* com.razbank.razbank.repositories.customer.*.*(..))")
    private void forDaoPackage() {}

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {}


    @Before("forAppFlow()")
    public void before(JoinPoint joinpoint) {

        String method= joinpoint.getSignature().toShortString();

        logger.info("=====>> in @Before: calling method: {}",method);

        //display method arguments
        Object[] args = joinpoint.getArgs();
        for(Object temp: args){
            logger.info(temp.toString());
            if(temp instanceof Customer){
                Customer customer = (Customer) temp;
                logger.info(customer.toString());
            }
        }
    }


    @Around("forAppFlow()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        String method = proceedingJoinPoint.getSignature().toShortString();
        logger.info("===>>> Executing @Around on method {}",method);

        long begin=System.currentTimeMillis();
        Object result;
        try {
            result = proceedingJoinPoint.proceed();
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            logger.error("Error in Customer process");
            throw e;
        }
        long end=System.currentTimeMillis();

        long duration = end-begin;
        logger.info("=======>> Duration: {} miliseconds",duration);
        return result;
    }


    @After("forAppFlow()")
    public void after(JoinPoint theJoinPoint){
        String method = theJoinPoint.getSignature().toShortString();
        logger.info("===>>> Executing @AFTER on method {}",method);

    }

    //add @afterReturning advice
    @AfterReturning(pointcut = "forAppFlow()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        //display method we are calling
        String method= joinPoint.getSignature().toShortString();
        logger.info("=====>> in @AfterReturning: from method: {}",method);
        //display the arguments to the method
        logger.info("=======>> result: {}",result);
    }

    @AfterThrowing(pointcut = "forControllerPackage()", throwing = "theExc")
    public void afterThrowing(JoinPoint theJoinPoint, Throwable theExc ){
        String method = theJoinPoint.getSignature().toShortString();
        logger.info("===>>> Executing @afterThrowing on method {}",method);
        logger.info("===>>> the exception is: {0}",theExc);

    }






}
