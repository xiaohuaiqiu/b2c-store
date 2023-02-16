package com.zhq.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User implements Serializable {
    public static final Long serialVersionUID = 1L;
    @JsonProperty("user_id") //jackson的注解，用于进行属性格式化
    @TableId(type = IdType.AUTO)
    private Integer userId; //用户id
    @Length(min = 6)
    private String userName; //用户名称
    @JsonInclude(JsonInclude.Include.NON_ABSENT)//当这个值不为空的时候生成Json，为空就不生成Json ，@JsonIgnore //忽略属性 不生成json 不接收json
    @NotBlank
    private String password; //用户密码
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @NotBlank
    private String userPhonenumber; //用户手机号码

}
