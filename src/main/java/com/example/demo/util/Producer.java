package com.example.demo.util;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

@Component
public class Producer {
    @Value("${apache.rocketmq.producer.producerGroup}")
    private String producerGroup;

    private DefaultMQProducer producer;
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @PostConstruct
    public void defaultMQProducer(){
        //生产者的组名
        producer = new DefaultMQProducer(producerGroup);
        //指定NameServer地址
        producer.setNamesrvAddr(namesrvAddr);
        producer.setVipChannelEnabled(false);

        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void send(String topic,String tags,String body) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message(topic, tags, body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        SendResult send = producer.send(message);
        stopWatch.stop();
    }
}
