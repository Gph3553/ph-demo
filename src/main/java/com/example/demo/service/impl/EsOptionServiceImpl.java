package com.example.demo.service.impl;

import com.example.demo.model.CategoryDto;
import com.example.demo.model.NrdShareAttr;
import com.example.demo.model.ResourcesCatalogue;
import com.example.demo.service.EsOptionService;
import com.example.demo.service.IResourcesCatalogueService;
import com.example.demo.service.IResourcesClassificationService;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.aggregations.metrics.ValueCount;
import org.elasticsearch.search.aggregations.metrics.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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
        String[] strings = new String[5];
        strings[0] = "id";
        strings[1] = "resourceName";
        strings[2] = "organizationId";
        strings[3] = "organizationName";
       // strings[4] = "createTm";
        strings[4] = "reviewStatus";
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
                .map(Optional::get).collect(toList());

        return true;
    }

    @Override
    public void sumAfterTwiceAgg() {
       //设置索引
        SearchRequest searchRequest = new SearchRequest(RESOURCES_INDEX);
        //构建查询
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //按照部门id进行聚合
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("organizationId",1467);
        RangeQueryBuilder createTm = QueryBuilders.rangeQuery("createTm");
        createTm.gte("2020-06-10");
        createTm.lte("2020-06-26");
        boolQueryBuilder.must(createTm);
        searchSourceBuilder.query(boolQueryBuilder);
        //按时间聚合
        DateHistogramAggregationBuilder aggregationBuilder = AggregationBuilders.dateHistogram("time_count").field("createTm").dateHistogramInterval(DateHistogramInterval.MONTH).format("yyyy-MM-dd")
                .order(BucketOrder.aggregation("tx_sum", false));
        aggregationBuilder.subAggregation(AggregationBuilders.count("tx_sum").field("organizationId"));
        searchSourceBuilder.aggregation(aggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        //发送请求
        try {
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //获取聚合结果
            LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
            Aggregations aggregations = search.getAggregations();
            Aggregation timeCount = aggregations.get("time_count");
            List<? extends Histogram.Bucket> buckets = ((Histogram) timeCount).getBuckets();
            for(Histogram.Bucket bucket:buckets){
                String keyAsString = bucket.getKeyAsString();
                Sum sum = bucket.getAggregations().get("tx_sum");
                map.put(keyAsString,sum.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //es求和
    @Override
    public int sumOrg() throws IOException {
        //索引
        SearchRequest searchRequest = new SearchRequest(RESOURCES_INDEX);
        //构建查询
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder field = AggregationBuilders.terms("sum").field("organizationId");
        ValueCountAggregationBuilder field1 = AggregationBuilders.count("sumAgg").field("id");
        field.subAggregation(field1);
        searchSourceBuilder.aggregation(field);
        searchRequest.source(searchSourceBuilder);
        //执行查询
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //获取结果
        Terms sum = search.getAggregations().get("sum");
        List<? extends Terms.Bucket> buckets = sum.getBuckets();
        buckets.stream().forEach(m ->{
            ValueCount sumAgg = m.getAggregations().get("sumAgg");
            System.out.println("机构:"+m.getKey()+"数量:"+sumAgg.getValue());
        });
        return 0;
    }

    @Override
    public Boolean fieldsSum() {
        //索引
        SearchRequest searchRequest = new SearchRequest(RESOURCES_INDEX);
        //构建查询
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder field = AggregationBuilders.terms("sum").field("organizationId");
        TermsAggregationBuilder revie = AggregationBuilders.terms("rev").field("reviewStatus.keyword");
        ValueCountAggregationBuilder field1 = AggregationBuilders.count("sumAgg").field("id");
        revie.subAggregation(field1);
        searchSourceBuilder.aggregation(field.subAggregation(revie));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //得到这个分组数据聚合
            Terms sum = search.getAggregations().get("sum");
            sum.getBuckets().stream().forEach(m ->{
                Terms rev = m.getAggregations().get("rev");
                rev.getBuckets().stream().forEach(t ->{
                    ValueCount sumAgg = t.getAggregations().get("sumAgg");
                    System.out.println("机构:"+m.getKey()+"状态:"+t.getKey()+"数量:"+sumAgg.getValue());
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<String> esThink(String keyword) {
        //多字段匹配
//        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword,"resourceName","resourcePinyin");
//        //索引
//        SearchRequest searchRequest = new SearchRequest(RESOURCES_INDEX);
//        //构建查询
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(multiMatchQueryBuilder).from(0).size(20);
//        searchRequest.source(searchSourceBuilder);
//
//        try {
//            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//            SearchHits hits = search.getHits();
//            for(SearchHit hit:hits){
//                System.out.println("资源名称:"+hit.getSourceAsMap().get("resourceName")+",资源拼音:"+hit.getSourceAsMap().get("resourcePinyin"));
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		// 模糊查询
		boolQueryBuilder.must(QueryBuilders.boolQuery()
				.should(QueryBuilders.matchPhraseQuery("resourceName", keyword)));

		// 设置返回的字段
		String[] fields = new String[1];
		fields[0] = "resourceName";
		String[] ex = new String[0];

		SearchResponse searchResponse = new SearchResponse();
		try {
			searchResponse = restHighLevelClient.search(new SearchRequest(RESOURCES_INDEX)
							.source(new SearchSourceBuilder()
									.size(10)
									.from(0)
									.query(boolQueryBuilder)
									.fetchSource(fields, ex)
							),
					RequestOptions.DEFAULT);
		} catch (IOException e) {

		}
		SearchHits searchHits = searchResponse.getHits();
		Set<ResourcesCatalogue> collect = Arrays.stream(searchHits.getHits())
				.map(e -> ResourcesCatalogue.readValue(e.getSourceAsString()))
				.filter(Optional::isPresent).map(Optional::get).collect(toSet());
		return collect.stream().map(ResourcesCatalogue::getResourceName).collect(toList());
    }

    @Override
    public List<String> esThinkDl(String keyword) {

        CompletionSuggestionBuilder complete = SuggestBuilders.completionSuggestion("resourceName").prefix(keyword).size(10).skipDuplicates(true);
        //索引
        SearchRequest searchRequest = new SearchRequest(RESOURCES_INDEX);
        //查询
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("resName",complete);
        SearchResponse search = new SearchResponse();
        try {
            search = restHighLevelClient.search(searchRequest.source(new SearchSourceBuilder().size(10).from(0).suggest(suggestBuilder)),RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Suggest suggest = search.getSuggest();
        //没有任何数据
        if(suggest == null){
            return new ArrayList<String>();
        }
        List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> resName = suggest.getSuggestion("resName").getEntries();
        ArrayList<String> strings = new ArrayList<>();
        if(!CollectionUtils.isEmpty(resName)){
            resName.stream().forEach(t ->{
                strings.add(t.getText().toString());
            });
        }else {
            return null;
        }
        return strings;
    }

    @Override
    public Boolean analyIndex() {
        List<ResourcesCatalogue> list = resourcesCatalogueService.lambdaQuery().eq(ResourcesCatalogue::getDelStatus, false).ne(ResourcesCatalogue::getShareAttr, NrdShareAttr.NO_SHARE).list();
        if(!CollectionUtils.isEmpty(list)){
            updateSuggest(list.get(3));
        }

        return null;
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

    /**
     * 进行分词生成索引
     */
    private void updateSuggest(ResourcesCatalogue resourcesCatalogue){
        AnalyzeRequestBuilder analyzeRequestBuilder = new AnalyzeRequestBuilder((ElasticsearchClient) restHighLevelClient, AnalyzeAction.INSTANCE,RESOURCES_INDEX,"resourceName",resourcesCatalogue.getResourceName());
        analyzeRequestBuilder.setAnalyzer("ik_smart");
        AnalyzeResponse analyzeTokens = analyzeRequestBuilder.get();
        List<AnalyzeResponse.AnalyzeToken> tokens = analyzeTokens.getTokens();
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<ResourcesCatalogue> resourcesCatalogues = new ArrayList<>();
        for(AnalyzeResponse.AnalyzeToken token:tokens){
            if(token.getTerm().length() < 2){
                continue;
            }
            if(! strings.contains(token.getTerm())){
                strings.add(token.getTerm());
            }
        }

        //关键字处理
//        if(!CollectionUtils.isEmpty(strings)){
//            strings.stream().forEach(t ->{
//                ResourcesCatalogue rce = new ResourcesCatalogue();
//                ArrayList<String> strings1 = new ArrayList<>();
//                strings1.add(t);
//                strings1.add(Pinyin.list2StringSkipNull(Pinyin.pinyin(t),""));
//                strings1.add(Pinyin.list2StringSkipNull(Pinyin.firstChar(t),""));
//                Completion completion = new Completion(new String[]{Pinyin.list2String(strings1)});
//                completion.setInput(new String[]{Pinyin.list2String(strings1)});
//                rce.setId();
//            });
//
//        }
    }



















}
