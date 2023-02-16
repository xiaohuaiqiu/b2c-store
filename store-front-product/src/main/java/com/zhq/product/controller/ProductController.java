package com.zhq.product.controller;

import com.zhq.param.*;
import com.zhq.product.service.ProductService;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 商品模块的controller
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/promo")
    public R promo(@RequestBody ProductPromoParam productPromoParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("数据查询失败!");
        }
        return productService.promo(productPromoParam.getCategoryName());
    }

    @PostMapping("/hots")
    public R promo(@RequestBody @Validated ProductHotParam productHotParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("数据查询失败!");
        }
        return productService.hots(productHotParam);
    }

    @PostMapping("category/list")
    public R clist(){
        return productService.clist();
    }

    /**
     * 类别商品 有id
     */
    @PostMapping("bycategory")
    public R byCategory(@RequestBody @Validated ProductIdsParam productIdsParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("查询失败，参数异常!");
        }
        return productService.byCategory(productIdsParam);
    }

    /**
     * 类别商品 没id
     */
    @PostMapping("all")
    public R all(@RequestBody @Validated ProductIdsParam productIdsParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("查询失败，参数异常!");
        }
        return productService.byCategory(productIdsParam);
    }
    /**
     * 商品详细情况查询
     */
    @PostMapping("/detail")
    public R detail(@RequestBody @Validated ProductIdParam idParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("查询失败，参数异常!");
        }
        return productService.detail(idParam.getProductID());
    }

    /**
     * 商品图片查询
     */
    @PostMapping("/pictures")
    public R picture(@RequestBody @Validated ProductIdParam idParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("商品图片查询失败，参数异常!");
        }
        return productService.picture(idParam.getProductID());
    }

    /**
     * 商品搜索
     */
    @PostMapping("/search")
    public R search(@RequestBody ProductSearchParam productSearchParam){
        return productService.search(productSearchParam);
    }
}
