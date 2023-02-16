package com.zhq.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhq.pojo.Address;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 地址接收值的param
 */
@Data
public class AddressParam {
    @JsonProperty("user_id")
    @NotNull
    private Integer userId;
    private Address add;
}
