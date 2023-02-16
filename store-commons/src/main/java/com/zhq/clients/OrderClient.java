package com.zhq.clients;

import com.zhq.param.PageParam;
import com.zhq.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 订单可以调用的客户端
 */
@FeignClient("order-service")
public interface OrderClient {

    @PostMapping("/order/remove/check")
    R check(@RequestBody Integer productId);

    /**
     * 后台管理的订单展示客户端
     *
     * @param pageParam
     * @return
     */
    @PostMapping("/order/admin/list")
    R OrderList(@RequestBody PageParam pageParam);
}
