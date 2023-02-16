package com.zhq.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhq.param.OrderParam;
import com.zhq.param.PageParam;
import com.zhq.pojo.Order;
import com.zhq.utils.R;

public interface OrderService extends IService<Order> {
    /**
     * 进行订单数据保存业务
     *
     * @param orderParam
     * @return
     */
    R save(OrderParam orderParam);

    /**
     * 分组查询购物车订单数据
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 检查订单中是否有商品引用
     * @param productId
     * @return
     */
    R check(Integer productId);

    /**
     * 查询全部订单
     * @param pageParam
     * @return
     */
    R adminList(PageParam pageParam);

}
