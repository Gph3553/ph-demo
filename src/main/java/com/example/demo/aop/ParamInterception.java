package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <h3>dictory</h3>
 * <p>注解测试</p>
 *
 * @author : PanhuGao
 * @date : 2020-05-14 15:39
 **/
@Aspect
@Component
public class ParamInterception {

    @Pointcut("execution(* com.example.demo.service.*.*(..)) && @annotation(com.example.demo.annotation.ActionLogAnno)")
    private void anyMethod(){}

    @Before(value = "anyMethod()")
    public void before(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature= (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        System.out.println("++++++++++++++++++"+method.getName()+"+++++++++++++++++++++");
        Class<? extends JoinPoint> aClass = joinPoint.getClass();
        //获取所有的字段
        Field[] declaredFields = aClass.getDeclaredFields();
        //循环遍历字段，获取字段对应的属性值
        for(Field field:declaredFields){
            //如果不为空，设置可见性，然后返回
            field.setAccessible(true);

            try {
                if(field.get(joinPoint) != null){
                    System.out.println(field.getName()+ "============="+ field.get(joinPoint));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


    }
}
