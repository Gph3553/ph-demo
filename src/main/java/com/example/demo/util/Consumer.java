package com.example.demo.util;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Consumer implements CommandLineRunner {
    @Value("${apache.rocketmq.consumer.pushConsumer}")
    private String pushConsumer;
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    public void messageListener(){
        DefaultMQPushConsumer springBootRocketMqGroup = new DefaultMQPushConsumer("SpringBootRocketMqGroup");
        springBootRocketMqGroup.setNamesrvAddr(namesrvAddr);
        try {
            //订阅PushTopic下Tag为push的消息，都订阅消息
            springBootRocketMqGroup.subscribe("PushTopic","push");
            //程序第一次启动从消息队列头获取数据
            springBootRocketMqGroup.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //可以修改每次消费消息的数量，默认设置是每次消费一条
            springBootRocketMqGroup.setConsumeMessageBatchMaxSize(1);
            //在此监听中消费信息，并返回消费的状态信息
            springBootRocketMqGroup.registerMessageListener((MessageListenerConcurrently)(msgs,context) ->{
                //会把不同的消息分别放置到不同的队列中
                for(Message msg:msgs){
                    System.out.println("接收到了消息:" + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            springBootRocketMqGroup.start();

        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
           this.messageListener();
    }
}
