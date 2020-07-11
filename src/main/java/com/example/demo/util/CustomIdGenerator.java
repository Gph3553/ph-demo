//package com.example.demo.util;
//
//import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.atomic.AtomicLong;
//@Component
//public class CustomIdGenerator implements IdentifierGenerator {
//    private final AtomicLong atomicLong = new AtomicLong(1);
//    @Override
//    public Number nextId(Object entity) {
//        final long id  = atomicLong.getAndAdd(1);
//        return id;
//    }
//}
