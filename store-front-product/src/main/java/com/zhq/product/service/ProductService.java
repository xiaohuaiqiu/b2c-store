package com.zhq.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhq.param.ProductHotParam;
import com.zhq.param.ProductIdsParam;
import com.zhq.param.ProductSaveParam;
import com.zhq.param.ProductSearchParam;
import com.zhq.pojo.Product;
import com.zhq.to.OrderToProduct;
import com.zhq.utils.R;

import java.util.List;

public interface ProductService extends IService<Product> {
    /**
     * 根据单类别名称 查询热门商品 最多7条数据
     * @param categoryName 类别名称
     * @return R
     */
    R promo(String categoryName);

    /**
     * 多类别热门商品查询 根据类别名称集合 至多查询7条！
     * @param productHotParam 类别名称集合
     * @return R
     */
    R hots(ProductHotParam productHotParam);

    /**
     * 查询类别商品集合
     * @return
     */
    R clist();

    /**
     * 查询类别商品，进行返回
     * 如果传入了id 根据id查
     * 没传入 直接查询全部
     * @param productIdsParam
     * @return
     */
    R byCategory(ProductIdsParam productIdsParam);

    /**
     * 根据商品id查询商品详情信息
     * @param productID
     * @return
     */
    R detail(Integer productID);

    /**
     * 商品图片查询根据id
     * @param productID
     * @return
     */
    R picture(Integer productID);

    /**
     * 搜索服务调用，获取全部商品数据
     * 进行同步
     * @return
     */
    List<Product> allList();

    /**
     * 搜索商品功能
     * @return
     */
    R search(ProductSearchParam productSearchParam);

    /**
     * 根据商品id集合查询商品信息
     * @param productIds
     * @return
     */
    R ids(List<Integer> productIds);

    /**
     * 根据商品id查询商品集合
     * @param productIds
     * @return
     */
    List<Product> cartList(List<Integer> productIds);

    /**
     * 修改库存和增加销售量
     * @param orderToProducts
     */
    void subNumber(List<OrderToProduct> orderToProducts);

    /**
     * 类别对应的商品数量的查询
     * @param categoryId
     * @return
     */
    Long adminCount(Integer categoryId);

    /**
     * 商品保存的业务
     * @param productSaveParam
     * @return
     */
    R adminSave(ProductSaveParam productSaveParam);

    /**
     * 商品数据的更新
     * 1.更新商品数据
     * 2.同步搜索数据服务
     * @param product
     * @return
     */
    R adminUpdate(Product product);

    /**
     * 商品删除
     * @param productId
     * @return
     */
    R adminRemove(Integer productId);
}
