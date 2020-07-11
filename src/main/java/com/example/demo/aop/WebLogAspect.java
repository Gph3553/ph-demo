package com.example.demo.aop;

import com.dfjx.sendlog.sink.SinkLogToKafka;
import com.example.demo.annotation.WebLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * <h3>dictory</h3>
 * <p>日志切面</p>
 * @author : PanhuGao
 * @date : 2020-05-23 23:23
 **/
@Component
@Aspect
@Slf4j
public class WebLogAspect {
    private static final  String LINE_SEPARATOR = System.lineSeparator();
    @Pointcut("@annotation(com.example.demo.annotation.WebLog)")
    public void webLog(){}

     @Autowired
     private SinkLogToKafka sinkLogToKafka;
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint propertyDescriptor) throws Throwable {

        //保存日志
        MethodSignature signature = (MethodSignature) propertyDescriptor.getSignature();
        Method method = signature.getMethod();
        WebLog webLog = method.getAnnotation(WebLog.class);
        if(webLog!=null){
            String aspectLogDescription = getAspectLogDescription(propertyDescriptor);
            return aspectLogDescription;
        }
        return null;
    }


    public String getAspectLogDescription(JoinPoint joinPoint) throws ClassNotFoundException {
        String name = joinPoint.getTarget().getClass().getName();
        String name1 = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Class<?> aClass = Class.forName(name);
        Method[] methods = aClass.getMethods();
        StringBuilder stringBuilder = new StringBuilder("");
        for(Method method : methods){
            if(method.getName().equals(name1)){
                Class<?>[] parameterTypes = method.getParameterTypes();
                if(parameterTypes.length == args.length){
                    stringBuilder.append(method.getAnnotation(WebLog.class).description());
                    break;
                }
            }
        }
        return stringBuilder.toString();
    }
}
