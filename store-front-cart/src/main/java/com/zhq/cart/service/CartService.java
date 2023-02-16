package com.zhq.cart.service;

import com.zhq.param.CartSaveParam;
import com.zhq.pojo.Cart;
import com.zhq.utils.R;

import java.util.List;

public interface CartService {

    /**
     * 添加商品到购物车
     * @param cartSaveParam
     * @return
     */
    R save(CartSaveParam cartSaveParam);

    /**
     * 根据用户id查询购物车商品
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 更新购物车数量
     * @param cart
     * @return
     */
    R update(Cart cart);

    /**
     * 删除购物车数据
     * @param cart
     * @return
     */
    R remove(Cart cart);

    /**
     * 清空对应id的购物车项
     * @param cartIds
     */
    void clearIdS(List<Integer> cartIds);

    /**
     * 查询购物车项
     * @param productId
     * @return
     */
    R check(Integer productId);

}
