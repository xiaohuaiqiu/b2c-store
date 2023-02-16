package com.zhq.order.controller;

import com.zhq.order.service.OrderService;
import com.zhq.param.CartListParam;
import com.zhq.param.OrderParam;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单的controller
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 生成订单的功能实现
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody OrderParam orderParam){
        return orderService.save(orderParam);
    }

    /**
     * 订单展示
     * @param cartListParam
     * @param result
     * @return
     */
    @PostMapping("/list")
    public R list(@RequestBody @Validated CartListParam cartListParam, BindingResult result){ //复用CartListParam 因为只要传入一个user_id
        if (result.hasErrors()){
            return R.fail("参数异常,获取失败");
        }
        return orderService.list(cartListParam.getUserId());
    }

    /**
     * 后台删除商品之检查订单中是否有该商品
     * @param productId
     * @return
     */
    @PostMapping("/remove/check")
    public R save(@RequestBody Integer productId){
        return orderService.check(productId);
    }

}
