package com.zhq.order.controller;

import com.zhq.order.service.OrderService;
import com.zhq.param.PageParam;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理订单功能
 */
@RestController
@RequestMapping("/order")
public class OrderAdminController {
    @Autowired
    private OrderService orderService;

    /**
     * 订单模块展示功能
     *
     * @return
     */
    @PostMapping("/admin/list")
    public R list(@RequestBody PageParam pageParam) {
        return orderService.adminList(pageParam);
    }
}
