package com.example.demo.service.impl;

import com.example.demo.service.IdGenger;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class SnowflowerGerer implements IdGenger {
    private final AtomicLong atomicLong = new AtomicLong(1);
    @Override
    public Long getId() {
        final long id  = atomicLong.getAndAdd(1)+2L;
        System.out.println("雪花ID:"+ id);
        return id;
    }
}
