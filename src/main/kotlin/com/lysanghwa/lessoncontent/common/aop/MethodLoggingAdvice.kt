package com.lysanghwa.lessoncontent.common.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class MethodLoggingAdvice {

    private val logger = LoggerFactory.getLogger(MethodLoggingAdvice::class.java)

    @Around("execution(* com.lysanghwa.lessoncontent.service.*.*(..))")
    fun logServiceLayerMethodExecution(joinPoint: ProceedingJoinPoint): Any? {
        val className = joinPoint.target.javaClass.simpleName
        val methodName = joinPoint.signature.name
        val args = joinPoint.args

        logger.info("$className.$methodName() start")
        logger.debug("$className.$methodName() start with args=${args.joinToString()}")
        val result = joinPoint.proceed()
        logger.debug("{}.{}() end with result={}", className, methodName, result)
        logger.info("$className.$methodName() end")

        return result
    }
}
