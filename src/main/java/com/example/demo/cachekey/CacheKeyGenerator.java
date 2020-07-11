package com.example.demo.cachekey;

import org.aspectj.lang.ProceedingJoinPoint;

public interface CacheKeyGenerator {
    /**
     * 获取AOP参数，生成指定缓存Key
     */
    String getLocKey(ProceedingJoinPoint pjp);
}
