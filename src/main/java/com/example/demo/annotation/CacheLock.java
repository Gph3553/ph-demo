package com.example.demo.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheLock {
    /**
     * redis 锁key的前缀
     */
    String prefix() default "";

    /**
     * 过期秒数
     */
    int expire() default 20;

    /**
     * 超时时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * Key的分隔符
     */
    String delimiter() default ":";
}
