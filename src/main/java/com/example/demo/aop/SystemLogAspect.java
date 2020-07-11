package com.example.demo.aop;

import com.dfjx.sendlog.model.UserActionLog;
import com.dfjx.sendlog.sink.SinkLogToKafka;
import com.example.demo.annotation.SystemLog;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class SystemLogAspect {
    @Pointcut("@annotation(com.example.demo.annotation.SystemLog)")
    public void systemLog(){}
    @Autowired
    private SinkLogToKafka sinkLogToKafka;
    @Around("systemLog()")
    public Object doAround(ProceedingJoinPoint propertyDescriptor) throws Throwable {
        Date beginDate = new Date();
        Gson gson = new Gson();
        //执行方法
        Object proceed = propertyDescriptor.proceed();
        //保存日志
        //类名称
        String name = propertyDescriptor.getTarget().getClass().getName();
        //
        String name1 = propertyDescriptor.getSignature().getName();
        Class<?> aClass = Class.forName(name);
        Method[] methods = aClass.getMethods();
        for(Method method :methods){
            if(method.getName().equals(name1)){
                SystemLog systemLog = method.getAnnotation(SystemLog.class);
                if(systemLog!=null){
                    String s = gson.toJson(propertyDescriptor.getArgs());
                    saveSysLog(systemLog,s,beginDate);
                }
            }
        }

        return proceed;
    }

    private void saveSysLog(SystemLog systemLog, String params, Date beginDate){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(beginDate);

        UserActionLog userActionLog = new UserActionLog();
        userActionLog.setOptTime(dateString);
        userActionLog.setVisitModule(systemLog.visitModule().getModule());
        userActionLog.setFunction(systemLog.functionModule());
        userActionLog.setOptContent(systemLog.optContent());
        userActionLog.setRequestPra(params);
        userActionLog.setServicePath(request.getRequestURL().toString());

        sinkLogToKafka.sendUserActionLog(dateString,systemLog.visitModule().getModule(),systemLog.functionModule(),systemLog.optContent(),params,request.getRequestURL().toString());


    }
}
