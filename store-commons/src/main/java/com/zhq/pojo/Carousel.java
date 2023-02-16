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
 * 轮播图实体类
 */
@TableName("carousel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carousel implements Serializable {
    public static final Long SerialVersionUID = 1L;
    @JsonProperty("carousel_id")
    @TableId(value = "carousel_id",type = IdType.AUTO)
    private Integer carouselId;
    @TableField("img_path")
    private String imgPath; //图片路径
    private String describes; //介绍
    @JsonProperty("product_id")
    @TableField("product_id")
    private Integer productId; //产品编号
    private Integer priority; //优先级

}
