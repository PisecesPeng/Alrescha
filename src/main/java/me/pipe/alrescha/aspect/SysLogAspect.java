package me.pipe.alrescha.aspect;

import me.pipe.alrescha.annotation.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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

        /*
            // 打印出syslog注解的value
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();

            SysLog syslog = method.getAnnotation(SysLog.class);
            if(syslog != null){
                //注解上的描述
                System.out.println(syslog.value());
            }
        */

        return result;
    }

}
