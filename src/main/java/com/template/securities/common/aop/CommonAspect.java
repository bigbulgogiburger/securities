package com.template.securities.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 *
 * aspect를 적용하는 방법
 * 1. 해당 class를 컴포넌트로 등록
 * 2. 상위 클래스에(주로 main) @Import를 통해 선언
 */
@Aspect
@Component
@Slf4j
public class CommonAspect {

    /**
     * 간단하게 securities.user 하위 패키지의
     * 모든 메소드에 signature를 출력하는 aspect를 걸었다.
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.template.securities.user..*.*(..))")
    public Object justForDot(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("signature called : "+ joinPoint.getSignature().getDeclaringTypeName() +"."+joinPoint.getSignature().getName());
        return joinPoint.proceed();
    }

}
