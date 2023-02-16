package com.zhq.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品图片实体类
 */
@TableName("product_picture")
@Data
public class Picture implements Serializable {
    public static final Long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id; //自身id
    @TableField("product_id")
    @JsonProperty("product_id")
    private Integer productId; //商品id
    @TableField("product_picture")
    @JsonProperty("product_picture")
    private String productPicture; //图片地址
    private String intro; //描述


}
