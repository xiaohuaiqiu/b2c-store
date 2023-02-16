package com.zhq.clients;

import com.zhq.param.ProductCollectParam;
import com.zhq.param.ProductIdParam;
import com.zhq.param.ProductSaveParam;
import com.zhq.param.ProductSearchParam;
import com.zhq.pojo.Product;
import com.zhq.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 商品服务调用客户端
 */
@FeignClient(value = "product-service")
public interface ProductClient {
    /**
     * 搜索服务调用，进行全部数据查询！用于搜索数据库同步数据
     *
     * @return
     */
    @GetMapping("/product/list")
    List<Product> alllist();

    /**
     * 收藏服务调用
     *
     * @param productCollectParam
     * @return
     */
    @PostMapping("/product/collect/list")
    R productIds(@RequestBody ProductCollectParam productCollectParam);

    /**
     * 通过商品id查看商品详细信息
     *
     * @param productIdParam
     * @return
     */
    @PostMapping("/product/cart/detail")
    Product productDetail(@RequestBody ProductIdParam productIdParam);

    /**
     * 根据商品id查询商品集合
     */
    @PostMapping("/product/cart/list")
    List<Product> cartList(@RequestBody ProductCollectParam productCollectParam);

    /**
     * 查询类别对应的商品数量
     *
     * @param categoryId
     * @return
     */
    @PostMapping("/product/admin/count")
    Long adminCount(@RequestBody Integer categoryId);

    /**
     * 后台商品添加
     *
     * @param productSaveParam
     * @return
     */
    @PostMapping("/product/admin/save")
    R adminSave(@RequestBody ProductSaveParam productSaveParam);

    /**
     * 后台商品修改
     *
     * @param product
     * @return
     */
    @PostMapping("/product/admin/update")
    R adminUpdate(@RequestBody Product product);

    /**
     * 后台商品删除
     *
     * @param productId
     * @return
     */
    @PostMapping("/product/admin/remove")
    R adminRemove(@RequestBody Integer productId);
}
