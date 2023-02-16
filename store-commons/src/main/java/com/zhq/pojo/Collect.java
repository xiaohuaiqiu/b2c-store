package com.zhq.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收藏实体类
 */
@Data
@TableName("collect")
public class Collect implements Serializable {
    public static final Long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    public Integer id;
    @TableField("user_id")
    @JsonProperty("user_id")
    public Integer userId; //用户id
    @TableField("product_id")
    @JsonProperty("product_id")
    public Integer productId; //商品id
    @TableField("collect_time")
    @JsonProperty("collect_time")
    private Long collectTime;


}
