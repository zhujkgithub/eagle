package com.soaring.eagle.admin.config.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/28
 * Time: 14:41
 */

@Component
@Aspect
public class LoggingAspect {

    private final static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * 环绕增强
     * 切点表达式
     *
     * @param pjd
     * @return
     */
    @Around("execution(* com.soaring.eagle.admin..*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint pjd) {
        Object result = null;

        String sysUserName = "";

        String methodName = pjd.getSignature().getName();
        String declaringTypeName = pjd.getSignature().getDeclaringTypeName();
        // 执行目标方法  前置通知、后置通知 任何情况下必定执行，只有异常通知与返回通知生死两不相见
        try {
            // 前置通知
            logger.info(sysUserName + ": 类路径[" + declaringTypeName + "] 执行方法[" + methodName + "]开始,参数: " + Arrays.asList(pjd.getArgs()));
            result = pjd.proceed();
            // 返回通知
            logger.info(sysUserName + ": 类路径[" + declaringTypeName + "] 执行方法[" + methodName + "]结束,结果: " + result);
        } catch (Throwable e) {
            // 异常通知
//            System.out.println("The method " + methodName + " occurs expection : " + e);
//            throw new RuntimeException(e);
        } finally {
            // 后置通知
//            System.out.println("The method " + methodName + " ends");
        }

        return result;
    }
}
