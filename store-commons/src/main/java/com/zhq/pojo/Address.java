package com.zhq.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 地址实体类
 */
@Data
@TableName("address")
public class Address implements Serializable {
    public static final Long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotBlank
    private String linkman; //联系人
    @NotBlank
    private String phone; //手机号
    @NotBlank
    private String address; //地址
    @TableField("user_id")
    private Integer userId;
}
