package com.zhq.admin.service;

import com.zhq.param.PageParam;
import com.zhq.pojo.Category;
import com.zhq.utils.R;

public interface CategoryService {

    /**
     * 分页查询的方法
     *
     * @param pageParam
     * @return
     */
    R pageList(PageParam pageParam);

    /**
     * 插入数据
     *
     * @param category
     * @return
     */
    R save(Category category);

    /**
     * 删除数据
     *
     * @param categoryId
     * @return
     */
    R remove(Integer categoryId);

    /**
     * 修改数据
     * @param category
     * @return
     */
    R update(Category category);
}
