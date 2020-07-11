package com.example.demo.controller;

import com.example.demo.annotation.CacheLock;
import com.example.demo.annotation.CacheParm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LockController {
    @CacheLock(prefix = "test")
   @GetMapping("/test")
   public String query(@CacheParm(name = "token") @RequestParam String token) {
         return "success - " + token;
        }

}
