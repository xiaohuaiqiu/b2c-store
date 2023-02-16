package com.zhq.admin.controller;

import com.zhq.admin.service.AdminUserService;
import com.zhq.admin.service.ProductService;
import com.zhq.admin.utils.AliyunOSSUtils;
import com.zhq.param.ProductSaveParam;
import com.zhq.param.ProductSearchParam;
import com.zhq.pojo.Product;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 商品后台管理controller
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    /**
     * 商品展示和搜索
     *
     * @return
     */
    @GetMapping("/list")
    public R adminList(ProductSearchParam searchParam) {
        return productService.adminList(searchParam);
    }

    /**
     * 图片上传
     *
     * @return
     */
    @PostMapping("/upload")
    public R adminUpload(@RequestParam("img") MultipartFile img) throws Exception {
        String filename = img.getOriginalFilename(); //获取文件名

        filename = UUID.randomUUID().toString().replaceAll("-", "") + filename; //防止文件名重复!

        String type = img.getContentType(); //获取类型

        byte[] bytes = img.getBytes(); //获取图片内容

        int hours = 1000; //存储时间

        String url = aliyunOSSUtils.uploadImage(filename, bytes, type, hours);
        System.out.println("url=" + url);
        return R.ok("图片上传成功!", url);
    }

    /**
     * 商品添加
     *
     * @return
     */
    @PostMapping("/save")
    public R adminSave(ProductSaveParam productSaveParam) {
        return productService.adminSave(productSaveParam);
    }

    /**
     * 商品修改
     *
     * @return
     */
    @PostMapping("/update")
    public R adminUpdate(Product product) {
        return productService.adminUpdate(product);
    }

    /**
     * 商品删除
     *
     * @return
     */
    @PostMapping("/remove")
    public R adminRemove(Integer productId) {
        return productService.adminRemove(productId);
    }
}
