package com.zhq.category.service;

import com.zhq.param.PageParam;
import com.zhq.param.ProductHotParam;
import com.zhq.param.ProductIdsParam;
import com.zhq.pojo.Category;
import com.zhq.utils.R;

public interface CategoryService {
    /**
     * 根据类别名称查询类别对象
     *
     * @param categoryName
     * @return
     */
    R byName(String categoryName);

    /**
     * 根据转入的热门商品集合返回类别对应的id
     * @param productHotParam
     * @return
     */
    R hots(ProductHotParam productHotParam);

    /**
     * 查询类别数据，进行返回
     * @return r 类别数据集合
     */
    R list();

    /**
     * 分页查询
     * @param pageParam
     * @return
     */
    R listPage(PageParam pageParam);

    /**
     * 添加类别信息
     * @param category
     * @return
     */
    R adminSave(Category category);

    /**
     * 删除类别信息
     * @param categoryId
     * @return
     */
    R adminRemove(Integer categoryId);

    /**
     * 修改类别信息
     * @param category
     * @return
     */
    R adminUpdate(Category category);
}
