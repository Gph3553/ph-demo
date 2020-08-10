package com.example.demo.controller;

import com.example.demo.service.DictoryService;
import com.example.demo.util.Producer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @创建人 PanHu.Gao
 * @创建时间 2020/5/24
 * @描述
 */
@RestController
public class DictoryController {
    @Autowired
    private DictoryService dictoryService;

    @Autowired
    private Producer producer;

    //@WebLog(description = "测试切面")
    @PostMapping("/select/dictory")
    void dicInfo(Long id){
        dictoryService.dicOracle(id);
    };

    @PostMapping("/select/idGen")
    public void addDic(){
      dictoryService.addDataAccess();
    }



    //测试rocketmq
    @GetMapping("/select/rbq")
    public void pushMsg(String msg){
        try {
             producer.send("PushTopic","push",msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
    }
}
