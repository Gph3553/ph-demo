package com.example.demo.service.impl;

import com.example.demo.model.CategoryDto;
import com.example.demo.model.NrdShareAttr;
import com.example.demo.model.ResourcesCatalogue;
import com.example.demo.service.EsOptionService;
import com.example.demo.service.IResourcesCatalogueService;
import com.example.demo.service.IResourcesClassificationService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EsOptionServiceImpl implements EsOptionService {
    @Value("${es.index.resources}")
    private String RESOURCES_INDEX;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private IResourcesCatalogueService resourcesCatalogueService;
    @Autowired
    private IResourcesClassificationService resourcesClassificationService;

    @Override
    public Boolean initOption() {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(RESOURCES_INDEX);
        try {
             restHighLevelClient.indices().delete(deleteIndexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<CategoryDto> res = new ArrayList<>();
        List<ResourcesCatalogue> list = resourcesCatalogueService.lambdaQuery().eq(ResourcesCatalogue::getDelStatus, false).ne(ResourcesCatalogue::getShareAttr, NrdShareAttr.NO_SHARE).list();
        if(!CollectionUtils.isEmpty(list)){
            list.stream().forEach(t->{
                   createEsProductUtils(t);
            });
        }

        return true;
    }

    @Override
    public Boolean selResInfo() {
        SearchRequest searchRequest = new SearchRequest(RESOURCES_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        SearchResponse searchResponse = new SearchResponse();
        //返回的字段
        String[] strings = new String[6];
        strings[0] = "id";
        strings[1] = "resourceName";
        strings[2] = "organizationId";
        strings[3] = "organizationName";
        strings[4] = "createTm";
        strings[5] = "reviewStatus";
        String[] ex = new String[0];
        SearchSourceBuilder searchSourceBuilder1 = searchSourceBuilder.size(1000).from(0).query(QueryBuilders.matchAllQuery()).fetchSource(strings, ex);
        searchRequest.source(searchSourceBuilder1);
        try {
            searchResponse=   restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        List<ResourcesCatalogue> collect = Arrays.stream(hits.getHits()).map(e -> ResourcesCatalogue.readValue(e.getSourceAsString())).filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toList());

        return true;
    }

    public Boolean createEsProductUtils(ResourcesCatalogue resourceProduct){
        Assert.notNull(resourceProduct,"资源不能為空");
        IndexRequest request = new IndexRequest(RESOURCES_INDEX);
        request.id(String.valueOf(resourceProduct.getId()));
        String s = resourceProduct.toJson();
        request.source(s, XContentType.JSON);
        try {
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 根據id獲取資源或者目錄信息
     */
    private ResourcesCatalogue getResourceProduct(Long id, Boolean isResource){
            ResourcesCatalogue model = resourcesCatalogueService.getById(id);
            if(ObjectUtils.isEmpty(model)){
                return new ResourcesCatalogue();
            }
           return model;
    }



















}
