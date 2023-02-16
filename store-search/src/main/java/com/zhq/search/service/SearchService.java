package com.zhq.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhq.param.ProductSearchParam;
import com.zhq.pojo.Product;
import com.zhq.utils.R;

import java.io.IOException;

public interface SearchService {

    /**
     * 根据关键字和分页查询
     * @param
     * @return
     */
    R search(ProductSearchParam productSearchParam);

    /**
     * 商品同步的插入和更新
     * @param product
     * @return
     */
    R save(Product product) throws IOException;

    /**
     * 进行es库的商品删除
     * @param productId
     * @return
     */
    R remove(Integer productId) throws IOException;
}
