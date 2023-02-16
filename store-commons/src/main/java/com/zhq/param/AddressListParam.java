package com.zhq.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 查询地址集合参数接收
 */
@Data
public class AddressListParam {
    @NotNull
    @JsonProperty("user_id")
    private Integer userId;
}
