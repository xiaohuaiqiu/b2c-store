package com.zhq.search.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhq.param.ProductSearchParam;
import com.zhq.pojo.Product;
import com.zhq.search.service.SearchService;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 搜索模块的controller
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索功能的实现
     *
     * @param productParamsSearch
     * @return
     */
    @PostMapping("product")
    public R productList(@RequestBody ProductSearchParam productParamsSearch) {
        return searchService.search(productParamsSearch);
    }

    /**
     * 后台管理的添加功能的搜索同步之操作
     * 同步调用，进行商品插入 同步更新
     *
     * @param product
     * @return
     */
    @PostMapping("save")
    public R saveProduct(@RequestBody Product product) throws IOException {
        return searchService.save(product);
    }

    /**
     * 后台管理的添加功能的搜索同步之操作
     * 同步调用，进行商品删除!
     *
     * @param productId
     * @return
     */
    @PostMapping("remove")
    public R removeProduct(@RequestBody Integer productId) throws IOException {
        return searchService.remove(productId);
    }

}

