package com.zhq.admin.service;

import com.zhq.param.ProductSaveParam;
import com.zhq.param.ProductSearchParam;
import com.zhq.pojo.Product;
import com.zhq.utils.R;

public interface ProductService {

    /**
     * 查询全部数据和模搜索的数据
     * @param searchParam
     * @return
     */
    R adminList(ProductSearchParam searchParam);

    /**
     * 商品数据保存
     * @param productSaveParam
     * @return
     */
    R adminSave(ProductSaveParam productSaveParam);

    /**
     * 商品数据修改
     * @param product
     * @return
     */
    R adminUpdate(Product product);

    /**
     * 商品删除数据
     * @param productId
     * @return
     */
    R adminRemove(Integer productId);
}
