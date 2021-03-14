package com.safetynet.api.config;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RestLogAspect {

	@Before("allControllerPointCut()")
	public void logBeforeAllMethod(JoinPoint joinPoint) {
		System.out.println("--- BEFORE --- Method " + joinPoint.getSignature().getName() + "() with "
				+ Arrays.toString(joinPoint.getArgs()));
	}

	@After("allControllerPointCut()")
	public void logAfterAllMethod(JoinPoint joinPoint) {
		System.out.println("--- AFTER --- Method " + joinPoint.getSignature().getName() + "()"
				+ Arrays.toString(joinPoint.getArgs()));
	}

	@AfterThrowing(pointcut = "allControllerPointCut()", throwing = "e")
	public void logThrowAllMethod(JoinPoint joinPoint, Exception e) throws Throwable {
		System.out.println("--- EXCEPTION --- Method " + joinPoint.getSignature().getName());
		System.out.println("--- EXCEPTION DETAILS --- Method " + e.getMessage());
	}

	@Around("allControllerPointCut()")
	public Object logAroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		try {
			Object result = pjp.proceed();
			System.out.println("Resultat " + result);
			return result;
		} catch (Throwable e) {
			System.out.println("----AfterThrowing method avec Around  " + pjp.getSignature().getName() + "()"
					+ ":et leve exception!");
			throw e;
		}
	}

	@Pointcut("execution(* com.safetynet.api.controller.*.*(..))")
	public void allControllerPointCut() {
	}

}
