package com.zhq.param;

import com.zhq.pojo.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 热门商品参数接收对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductHotParam {
    @NotEmpty
    private List<String> categoryName; //类别名称
}
