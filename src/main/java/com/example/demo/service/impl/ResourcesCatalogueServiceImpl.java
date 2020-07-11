package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.ResourcesCatalogueMapper;
import com.example.demo.model.ResourcesCatalogue;
import com.example.demo.service.IResourcesCatalogueService;
import org.springframework.stereotype.Service;

@Service
public class ResourcesCatalogueServiceImpl extends ServiceImpl<ResourcesCatalogueMapper, ResourcesCatalogue> implements IResourcesCatalogueService {
}
