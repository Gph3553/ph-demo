package com.example.demo.annotation;

import com.example.demo.constant.SystemMudoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemLog {
    SystemMudoleEnum visitModule();//访问模块    //example: 用户认证 、业务模块
    String functionModule() default ""; //点击模块下的功能  //登录成功 登录失败 注册 注销 生成令牌
    String optContent() default ""; //具体操作内容  //例如查看了【我的申请】模块  周畅登录平台！

}
