package com.zhq.admin.controller;

import com.zhq.admin.service.OrderService;
import com.zhq.param.PageParam;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单的后台管理controller
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public R list(PageParam pageParam){
        return orderService.list(pageParam);
    }
}
