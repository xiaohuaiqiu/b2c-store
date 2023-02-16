package com.zhq.product.istener;

import com.zhq.product.service.ProductService;
import com.zhq.to.OrderToProduct;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品mq消息监听
 */
@Component //放入ioc容器
public class ProductRabbitMqListener {
    @Autowired
    private ProductService productService;
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "sub.queue"),
                    exchange = @Exchange("topic.ex"),
                    key = "sub.number"
            )
    )
    public void subNumber(List<OrderToProduct> orderToProducts){
     productService.subNumber(orderToProducts);
    }
}
