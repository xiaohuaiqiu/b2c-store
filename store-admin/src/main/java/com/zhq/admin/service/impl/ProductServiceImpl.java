package com.zhq.admin.service.impl;

import com.zhq.admin.service.ProductService;
import com.zhq.clients.ProductClient;
import com.zhq.clients.SearchClient;
import com.zhq.param.ProductSaveParam;
import com.zhq.param.ProductSearchParam;
import com.zhq.pojo.Product;
import com.zhq.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private SearchClient searchClient;
    @Autowired
    private ProductClient productClient;

    /**
     * 查询全部数据和模搜索的数据
     *
     * @param searchParam
     * @return
     */
    @Override
    public R adminList(ProductSearchParam searchParam) {
        R search = searchClient.search(searchParam);
        return search;
    }

    /**
     * 商品数据保存
     *
     * @param productSaveParam
     * @return
     */
    @Override
    public R adminSave(ProductSaveParam productSaveParam) {
        R r = productClient.adminSave(productSaveParam);
        return r;
    }

    /**
     * 商品数据修改
     *
     * @param product
     * @return
     */
    @Override
    public R adminUpdate(Product product) {
        R r = productClient.adminUpdate(product);
        return r;
    }

    /**
     * 商品删除数据
     *
     * @param productId
     * @return
     */
    @Override
    public R adminRemove(Integer productId) {
        R r = productClient.adminRemove(productId);
        return r;
    }
}
