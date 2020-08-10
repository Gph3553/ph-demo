package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.Dictionary;

public interface DictoryService extends IService<Dictionary> {
    void dicOracle(Long id);
    void addDataAccess();

}
