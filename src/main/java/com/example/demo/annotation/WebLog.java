package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @创建人 PanHu.Gao
 * @创建时间 2020/5/23
 * @描述
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLog {
    /**
     * 日志描述信息
     */
    String description() default "";
}
