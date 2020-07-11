package com.example.demo.controller;

import com.example.demo.service.DictoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 PanHu.Gao
 * @创建时间 2020/5/24
 * @描述
 */
@RestController
public class DictoryController {
    @Autowired
    private DictoryService dictoryService;

    //@WebLog(description = "测试切面")
    @PostMapping("/select/dictory")
    void dicInfo(Long id){
        dictoryService.dicOracle(id);
    };

    @PostMapping("/select/idGen")
    public void addDic(){
      dictoryService.addDataAccess();
    }
}
