package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.service.AuditOperService;
import com.example.demo.util.AuditThreadUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * <h3>dictory</h3>
 * <p>审核操作</p>
 *
 * @author : PanhuGao
 * @date : 2020-05-25 23:45
 **/
@Service
public class AuditOperServiceImpl implements AuditOperService {

    @Autowired
    private AuditThreadUtils auditThreadUtils;

    @Override
    public void auditData(CategoryDto categoryDto) {
        final CountDownLatch downLatch = new CountDownLatch(2);
        List<CategoryItemDto> categoryItemList = categoryDto.getCategoryItemList();
        List<BelongSystemDto> belongSystemList = categoryDto.getBelongSystemList();
//        AuditThreadUtils auditThreadUtils = new AuditThreadUtils();
        List<CategoryItem> categoryItems = new ArrayList<>();
        List<RCBelongSystem> belongSystems = new ArrayList<>();
        if(!CollectionUtils.isEmpty(categoryItemList)){
            categoryItems=  categoryItemList.stream().map(m ->{
                CategoryItem categoryItem = new CategoryItem();
                BeanUtils.copyProperties(m,categoryItem);
                return categoryItem;
            }).collect(Collectors.toList());
        }
        belongSystemList.parallelStream().map(t -> t.getId()!= null).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(belongSystemList)){
            belongSystems= belongSystemList.stream().map(t ->{
                RCBelongSystem belongSystem = new RCBelongSystem();
                BeanUtils.copyProperties(t,belongSystem);
                return belongSystem;
            }).collect(Collectors.toList());
        }
        auditThreadUtils.addItem(downLatch,categoryItems,66666666666666666L);
        auditThreadUtils.addSystem(downLatch,belongSystems,66666666666666666L);
        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("jieshu");

    }
}
