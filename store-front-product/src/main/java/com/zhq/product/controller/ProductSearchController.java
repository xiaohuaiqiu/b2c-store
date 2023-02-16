package com.zhq.product.controller;

import com.zhq.pojo.Product;
import com.zhq.product.service.ProductService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 搜索服务调用的controller
 */
@RestController
@RequestMapping("product")
public class ProductSearchController {
    @Autowired
    private ProductService productService;
    @GetMapping("/list")
    public List<Product> allList(){
        return productService.allList();
    }
}
