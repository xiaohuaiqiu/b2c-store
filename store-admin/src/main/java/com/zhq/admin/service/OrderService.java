package com.zhq.admin.service;

import com.zhq.param.PageParam;
import com.zhq.utils.R;

public interface OrderService {
    /**
     * 查询订单全部数据 带分页
     * @param pageParam
     * @return
     */
    R list(PageParam pageParam);
}
