package com.zhq.cart.controller;

import com.zhq.cart.service.CartService;
import com.zhq.param.AddressListParam;
import com.zhq.param.CartListParam;
import com.zhq.param.CartSaveParam;
import com.zhq.pojo.Cart;
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
 * 购物车控制controller
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 购物车添加功能实现
     *
     * @param cartSaveParam
     * @param result
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody @Validated CartSaveParam cartSaveParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数为空,添加失败!");
        }
        return cartService.save(cartSaveParam);
    }

    /**
     * 购物车商品展示功能
     *
     * @param cartListParam
     * @param result
     * @return
     */
    @PostMapping("/list")
    public R list(@RequestBody @Validated CartListParam cartListParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("购物车数据查看失败!");
        }
        return cartService.list(cartListParam.getUserId());
    }

    /**
     * 修改购物车的商品数量
     *
     * @param cart
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody Cart cart) {
        return cartService.update(cart);
    }

    /**
     * 删除购物车的商品数量
     *
     * @param cart
     * @return
     */
    @PostMapping("/remove")
    public R remove(@RequestBody Cart cart) {
        return cartService.remove(cart);
    }

    /**
     * 通过后台删除商品数据判断购物车中是否还有该商品
     *
     * @param productId
     * @return
     */
    @PostMapping("/remove/check")
    public R removeCheck(@RequestBody Integer productId) {
        return cartService.check(productId);
    }
}
