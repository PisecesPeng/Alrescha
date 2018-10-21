package me.pipe.alrescha.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SysLogAspect {

    @Pointcut("@annotation(me.pipe.alrescha.annotation.SysLog)")
    public void logPointCut() { }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();  //执行方法
        long runTime = System.currentTimeMillis() - beginTime;

        // TODO 数据库持久化

        return result;
    }

}
