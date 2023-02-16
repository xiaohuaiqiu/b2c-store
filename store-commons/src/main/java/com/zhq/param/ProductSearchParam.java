package com.zhq.param;

import lombok.Data;

/**
 * 搜索关键字和分页集合
 */
@Data
public class ProductSearchParam extends PageParam {
    private String search; //搜索

}

