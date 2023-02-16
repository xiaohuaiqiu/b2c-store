package com.zhq.admin.service.impl;

import com.zhq.admin.service.OrderService;
import com.zhq.clients.OrderClient;
import com.zhq.param.PageParam;
import com.zhq.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderClient orderClient;

    /**
     * 查询订单全部数据 带分页
     *
     * @param pageParam
     * @return
     */
    @Override
    public R list(PageParam pageParam) {
        R r = orderClient.OrderList(pageParam);
        return r;
    }
}
