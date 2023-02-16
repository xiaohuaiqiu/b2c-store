package com.zhq.search.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhq.doc.ProductDoc;
import com.zhq.param.ProductSearchParam;
import com.zhq.pojo.Product;
import com.zhq.search.service.SearchService;
import com.zhq.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 根据关键字和分页查询
     * 1.判断是否为null null查询全部 不是null 查询字段
     * 2.带上分页属性
     * 3.es查询
     * 4.结果处理
     *
     * @param productSearchParam
     * @return
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {
        SearchRequest searchRequest = new SearchRequest("product");

        String search = productSearchParam.getSearch();
        if (StringUtils.isEmpty(search)) {
            //为空 不添加all关键字，查询全部即可
            searchRequest.source().query(QueryBuilders.matchAllQuery()); //没有条件查询全部
        } else {
            //不为空 添加all字段匹配 根据all关键字查询
            searchRequest.source().query(QueryBuilders.matchQuery("all", search));
        }
        //进行分页数据添加
        searchRequest.source().from((productSearchParam.getCurrentPage() - 1) * productSearchParam.getPageSize()); //偏移量 （等于当前页数-1）*页容量
        searchRequest.source().size(productSearchParam.getPageSize());
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("查询错误!");
        }
        SearchHits hits = searchResponse.getHits();
        //查询符合的数量
        long total = hits.getTotalHits().value;

        //数据集合
        ArrayList<Object> productList = new ArrayList<>();
        //json处理器
        ObjectMapper objectMapper = new ObjectMapper();
        //拿到数据集合
        SearchHit[] hitsHits = hits.getHits();

        for (SearchHit hit : hitsHits) {
            //查询的内容数据 productDoc模块对应的json数据
            String sourceAsString = hit.getSourceAsString();
            Product product = null;
            try {
                //product all - product 如果没有all属性 会报错！jackson提供了忽略没有属性的注解
                product = objectMapper.readValue(sourceAsString, Product.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            productList.add(product);
        }
        // 商品服务 R msg total 符合的数量 |  data 商品集合
        R ok = R.ok(null, productList, total);
        return ok;
    }

    /**
     * 商品同步的插入和更新
     *
     * @param product
     * @return
     */
    @Override
    public R save(Product product) throws IOException {
        IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());

        ProductDoc productDoc = new ProductDoc(product); //传入商品对象

        ObjectMapper objectMapper = new ObjectMapper(); //转成json字符串
        String json = objectMapper.writeValueAsString(productDoc);

        indexRequest.source(json, XContentType.JSON);

        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return R.ok("数据同步成功!");
    }

    /**
     * 进行es库的商品删除
     *
     * @param productId
     * @return
     */
    @Override
    public R remove(Integer productId) throws IOException {
        DeleteRequest request = new DeleteRequest("product").id(productId.toString());
        restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        return R.ok("删除成功!");
    }
}
