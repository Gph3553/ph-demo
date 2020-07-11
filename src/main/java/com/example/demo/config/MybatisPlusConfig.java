package com.example.demo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.example.demo.handler.MySqlHandler;
import com.example.demo.service.IdGenger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisPlusConfig
 * @Description MybatisPlus 插件配置
 * @Author xujian
 * @Date 2019/6/13 19:21
 * @Version 1.0
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    @Bean
    @ConditionalOnBean(value = com.example.demo.service.IdGenger.class)
    public MetaObjectHandler myHandler(IdGenger genger){
        return new MySqlHandler(genger);
    }




}
