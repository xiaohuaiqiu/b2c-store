package com.zhq.product.controller;

import com.zhq.param.ProductCollectParam;
import com.zhq.param.ProductIdParam;
import com.zhq.pojo.Cart;
import com.zhq.pojo.Product;
import com.zhq.product.service.ProductService;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车调用商品的controller
 */
@RestController
@RequestMapping("product")
public class ProductCartController {
    @Autowired
    private ProductService productService;

    /**
     * 通过商品id查看商品详细信息
     *
     * @param productIdParam
     * @param result
     * @return
     */
    @PostMapping("/cart/detail")
    public Product cdetail(@RequestBody @Validated ProductIdParam productIdParam, BindingResult result) {
        if (result.hasErrors()) {
            return null;
        }
        R detail = productService.detail(productIdParam.getProductID());
        Product product = (Product) detail.getData(); //读取数据 进行强制转化
        return product;
    }

    /**
     * 购物车中商品
     * @return
     */
    @PostMapping("/cart/list")
    public List<Product> cartList(@RequestBody @Validated ProductCollectParam productCollectParam,
                                  BindingResult result){ //复用收藏的id集合
        if (result.hasErrors()) {
            return new ArrayList<Product>(); //返回空 前端写的太low了
        }
        return productService.cartList(productCollectParam.getProductIds());
    }
}
