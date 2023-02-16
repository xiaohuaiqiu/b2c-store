package com.zhq.clients;

import com.zhq.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 购物车调用的客户端
 */
@FeignClient("cart-service")
public interface CartClient {

    @PostMapping("/cart/remove/check")
    R check(@RequestBody Integer productId);
}
