package com.zhq.cart.listener;

import com.zhq.cart.service.CartService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 监听Mq消息
 */
@Component //放入ioc容器
public class CartRabbitMqListener {
    @Autowired
    private CartService cartService;

    /**
     * 清除已经被支付的购物车对应的ids
     *
     * @param cartIds
     */
    //监听注解
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "clear.queue"),
                    exchange = @Exchange(value = "topic.ex"),
                    key = "clear.cart"
            )
    )
    public void clear(List<Integer> cartIds) {

        cartService.clearIdS(cartIds);
    }

}
