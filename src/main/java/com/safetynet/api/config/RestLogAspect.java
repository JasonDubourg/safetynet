package com.safetynet.api.config;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
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

	public Object logAroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		// same with MethodBeforeAdvice
		System.out.println(" ----Before method avec Around " + pjp.getSignature().getName() + "()");
		try {
			// proceed to original method call
			Object result = pjp.proceed();
			// same with AfterReturningAdvice
			System.out.println(" ----After method avec Around " + pjp.getSignature().getName() + "()"
					+ " avec le resultat " + result);
			return result;

		} catch (Throwable e) {
			// same with ThrowsAdvice
			System.out.println("----AfterThrowing method avec Around  " + pjp.getSignature().getName() + "()"
					+ ":et leve exception!");
			throw e;
		}
	}

	@Pointcut("execution(* com.safetynet.api.controller.*.*(..))")
	public void allControllerPointCut() {
	}

}
