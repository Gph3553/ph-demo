package com.example.demo.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = {"SyslogTopic"})
    public void listen(ConsumerRecord record){
        System.out.println(record.topic() + ":" + record.value());
    }
}
