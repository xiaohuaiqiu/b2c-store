package com.zhq.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 接收前端参数的param
 * TODO：使用jsr 303的注解 进行参数校验
 */
@Data
public class UserCheckParam {
    @NotBlank //规定不能为空和空串
    private String userName; //注意参数名称,要等于前端传过来的JSON的key的名称
}
