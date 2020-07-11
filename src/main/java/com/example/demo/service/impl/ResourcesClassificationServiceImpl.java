package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.ResourcesClassificationMapper;
import com.example.demo.model.ResourcesClassification;
import com.example.demo.service.IResourcesClassificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourcesClassificationServiceImpl extends ServiceImpl<ResourcesClassificationMapper, ResourcesClassification> implements IResourcesClassificationService {
    @Override
    public ResourcesClassification getResourcesClassificationById(Long id) {
        if (id == null) {
            return null;
        }
        List<ResourcesClassification> list = this.lambdaQuery().eq(ResourcesClassification::getId, id)
                .eq(ResourcesClassification::getDelStatus, false).list();
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }
}
