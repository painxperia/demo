package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjun
 * @date 2021/6/21  17:00
 */
@Service
@Slf4j
public class UserServiceImpl<T> implements UserService<T> {
    @Resource
    private RestHighLevelClient restHighLevelClient;


    @Override
    public Boolean insertUser(User user) {
        IndexRequest indexRequest = new IndexRequest("test_es");
        indexRequest.id(user.getId().toString());
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        try {
            indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("index:{}", JSON.toJSONString(index));
        } catch (Exception e) {
            log.error("error:", e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean updateUser(User user) {
        UpdateRequest updateRequest = new UpdateRequest("test_es",  user.getId().toString());
        updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);
        updateRequest.docAsUpsert(true);
        try {
            updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            log.info("update:{}", JSON.toJSONString(update));
        } catch (Exception e) {
            log.error("error update:", e);
        }
        return true;
    }

    @Override
    public List<T> search(User user, Class<T> clazz) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 26));
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.sort("id", SortOrder.DESC);
        SearchRequest searchRequest = new SearchRequest("test_es");
        searchRequest.source(sourceBuilder);
        List<T> list = new ArrayList<>();
        try {
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("search:{}", JSON.toJSONString(search));
            SearchHits hits = search.getHits();
            long totalHits = hits.getTotalHits().value;
            log.info("totalHits:{}", totalHits);
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit searchHit : searchHits) {
                T t = JSON.parseObject(searchHit.getSourceAsString(), clazz);
                list.add(t);
            }
        } catch (Exception e) {

        }
        return list;
    }

}
