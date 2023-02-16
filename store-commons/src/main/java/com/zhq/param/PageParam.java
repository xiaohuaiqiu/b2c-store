package com.zhq.param;

import lombok.Data;

/**
 * 分页属性
 */
@Data
public class PageParam {
    private int currentPage = 1; //默认值
    private int pageSize = 15; //默认值 15
}
