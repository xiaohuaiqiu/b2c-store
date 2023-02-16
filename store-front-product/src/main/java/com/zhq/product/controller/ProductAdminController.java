package com.zhq.product.controller;

import com.zhq.param.ProductSaveParam;
import com.zhq.pojo.Product;
import com.zhq.product.service.ProductService;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品用于后台管理数据支持的controller
 */
@RestController
@RequestMapping("/product")
public class ProductAdminController {
    @Autowired
    private ProductService productService;

    /**
     * 商品数量查询
     *
     * @param categoryId
     * @return
     */
    @PostMapping("/admin/count")
    public Long adminCount(@RequestBody Integer categoryId) {
        return productService.adminCount(categoryId);
    }

    /**
     * 商品保存的业务
     *
     * @param productSaveParam
     * @return
     */
    @PostMapping("/admin/save")
    public R adminSave(@RequestBody ProductSaveParam productSaveParam) {
        return productService.adminSave(productSaveParam);
    }

    /**
     * 商品更新的业务
     *
     * @param product
     * @return
     */
    @PostMapping("/admin/update")
    public R adminSave(@RequestBody Product product) {
        return productService.adminUpdate(product);
    }

    /**
     * 商品删除的业务
     *
     * @param productId
     * @return
     */
    @PostMapping("/admin/remove")
    public R adminSave(@RequestBody Integer productId) {
        return productService.adminRemove(productId);
    }
}
