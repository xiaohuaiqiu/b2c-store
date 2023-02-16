package com.zhq.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录实体参数
 */
@Data
public class UserLoginParam {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
