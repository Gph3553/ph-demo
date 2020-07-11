package com.example.demo.cachekey.impl;

import com.example.demo.annotation.CacheLock;
import com.example.demo.annotation.CacheParm;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import com.example.demo.cachekey.CacheKeyGenerator;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
@Service
@Slf4j
public class LockKeyGenerator implements CacheKeyGenerator {
    @Override
    public String getLocKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock annotation = method.getAnnotation(CacheLock.class);
         Object[] args = pjp.getArgs();
        Parameter[] parameters = method.getParameters();
        StringBuilder stringBuilder = new StringBuilder();
        //默认解析方法里面带CacheParam注解的属性，如果没有尝试解析实体对象
        for(int i=0; i<parameters.length;i++){
            CacheParm annotation1 = parameters[i].getAnnotation(CacheParm.class);
            if(annotation1 == null){
                continue;
            }
            stringBuilder.append(annotation.delimiter()).append(args[i]);
        }
        if(StringUtils.isEmpty(stringBuilder.toString())){
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for(int i = 0; i<parameterAnnotations.length;i++){
                Object arg = args[i];
                Field[] declaredFields = arg.getClass().getDeclaredFields();
                for(Field field:declaredFields){
                    CacheParm annotation1 = field.getAnnotation(CacheParm.class);
                    if(annotation1 == null){
                        continue;
                    }
                    field.setAccessible(true);
                    stringBuilder.append(annotation.delimiter()).append(ReflectionUtils.getField(field,arg));
                }
            }
        }
        return annotation.prefix()+stringBuilder.toString();
    }
}
