package com.example.demo.aop;

import com.example.demo.annotation.CacheLock;
import com.example.demo.cachekey.CacheKeyGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;


@Aspect
@Configuration
public class LockMethodInteceptor {
    public LockMethodInteceptor(StringRedisTemplate stringRedisTemplate, CacheKeyGenerator cacheKeyGenerator){
        this.lockRedisTemplate = stringRedisTemplate;
        this.cacheKeyGenerator = cacheKeyGenerator;
    }
    private final StringRedisTemplate lockRedisTemplate;
    private final CacheKeyGenerator cacheKeyGenerator;

    @Around("execution(public * *(..))&& @annotation(com.example.demo.annotation.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp){
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if(StringUtils.isEmpty(lock.prefix())){
            throw new RuntimeException("lock key canot be null ...");
        }
        final String lockKey = cacheKeyGenerator.getLocKey(pjp);
        //key不存在才会设置成功
        try {
            final Boolean success = lockRedisTemplate.opsForValue().setIfAbsent(lockKey, "");
            if (success) {
                lockRedisTemplate.expire(lockKey, lock.expire(), lock.timeUnit());
            } else {
                throw new RuntimeException("请勿重复请求");
            }
            try{
                return pjp.proceed();
            }catch (Throwable throwable){
                throw  new RuntimeException("系统异常");
            }
        }finally {
           // lockRedisTemplate.delete(lockKey);
        }

    }
}
