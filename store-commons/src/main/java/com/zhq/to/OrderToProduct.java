package com.zhq.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单发送商品服务的实体
 */
@Data
public class OrderToProduct implements Serializable { //消息队列实现序列化接口

    public static final Long serialVersionUID = 1L;

    private Integer productId;
    private Integer num;
}
