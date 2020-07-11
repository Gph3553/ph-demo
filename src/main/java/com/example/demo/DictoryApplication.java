package com.example.demo;

import com.example.demo.cachekey.CacheKeyGenerator;
import com.example.demo.cachekey.impl.LockKeyGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.example.demo"})
@MapperScan(value = "com.example.demo.mapper")
public class DictoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DictoryApplication.class, args);
    }
    @Bean
  public CacheKeyGenerator cacheKeyGenerator() {
         return new LockKeyGenerator();
        }

}
