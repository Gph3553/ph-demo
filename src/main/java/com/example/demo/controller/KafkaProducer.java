package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducer {
  @Autowired
    private KafkaTemplate template;
    @PostMapping("/sendMsg")
    public String sendMsg(String topic,String message){
        template.send(topic,message);
        return "sucess";
    }

}
