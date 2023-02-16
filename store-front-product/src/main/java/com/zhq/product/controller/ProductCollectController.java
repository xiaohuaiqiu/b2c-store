package com.zhq.product.controller;

import com.zhq.param.ProductCollectParam;
import com.zhq.product.service.ProductService;
import com.zhq.utils.R;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品被收藏调用的controller
 */
@RestController
@RequestMapping("product")
public class ProductCollectController {

    @Autowired
    private ProductService productService;

    @PostMapping("collect/list")
    public R productIds(@RequestBody @Validated ProductCollectParam productCollectParam, BindingResult result){
        if (result.hasErrors()) {
            return R.ok("么有收藏数据!");
        }
        return productService.ids(productCollectParam.getProductIds());
    }
}
