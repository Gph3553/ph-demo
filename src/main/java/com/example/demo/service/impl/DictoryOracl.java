package com.example.demo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.annotation.SystemLog;
import com.example.demo.constant.SystemMudoleEnum;
import com.example.demo.mapper.DictoryMapper;
import com.example.demo.model.Dictionary;
import com.example.demo.service.DictoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <h3>dictory</h3>
 * <p>字典迁移</p>
 * @author : PanhuGao
 * @date : 2020-05-13 10:26
 **/

@Slf4j
@Service
public class DictoryOracl  extends ServiceImpl<DictoryMapper, Dictionary> implements DictoryService {
    @Override
    @SystemLog(visitModule = SystemMudoleEnum.RESOURCE_CATA, functionModule = "字典数据", optContent = "查询操作")
    public void dicOracle(Long id) {
//     //数据库信息项
//        List<Dictionary> list = this.lambdaQuery().eq(Dictionary::getCategoryItemId, id).list();
//        System.out.println(list.toString());
    }

    @Override
    public void addDataAccess() {
        Dictionary dictionary = new Dictionary();
        dictionary.setDbKey("001");
        dictionary.setDbValue("ceshi");
        dictionary.setCategoryItemId(1L);
        dictionary.setDelStatus(0);
        dictionary.setCreator(3L);
        dictionary.setModifier(3L);
        dictionary.setTenantId(3L);
        LocalDateTime now = LocalDateTime.now();
        dictionary.setCreateTm(now.toString());
        dictionary.setModifyTm(now.toString());
        this.save(dictionary);
    }


}
