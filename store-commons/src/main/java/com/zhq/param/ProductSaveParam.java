package com.zhq.param;

import com.zhq.pojo.Product;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 商品数据保存param
 */
@Data
public class ProductSaveParam extends Product {

    //商品详情图片地址, 多图片地址使用 + 号拼接
    private String pictures;
}
