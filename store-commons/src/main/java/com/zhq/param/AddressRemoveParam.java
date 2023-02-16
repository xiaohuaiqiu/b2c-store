package com.zhq.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 地址移除参数
 */
@Data
public class AddressRemoveParam {
    @NotNull
    private Integer id;
}
