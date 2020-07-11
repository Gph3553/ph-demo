package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.ResourcesClassification;

/**
 * 资源目录分类表
 *
 * @author chensong
 * @date 2020-02-24 14:47:05
 */
public interface IResourcesClassificationService extends IService<ResourcesClassification> {
    /**
     * 根据id查询对应的数据
     *
     * @param id
     * @return
     */
    ResourcesClassification getResourcesClassificationById(Long id);
}

