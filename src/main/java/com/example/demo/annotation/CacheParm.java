package com.example.demo.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER,ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheParm {
    /**
     * 字段名称
     */
    String name() default "";
}
