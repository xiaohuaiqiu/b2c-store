package com.zhq.collect.service;

import com.zhq.pojo.Collect;
import com.zhq.utils.R;

public interface CollectService {

    /**
     * 收藏添加的方法
     *
     * @return
     */
    R save(Collect collect);

    /**
     * 根据用户id查询商品集合
     * 收藏展示功能方法
     *
     * @return
     */
    R list(Integer userId);

    /**
     * 根据商品id删除商品
     * @param collect
     * @return
     */
    R remove(Collect collect);

    /**
     * 根据商品id删除数据
     * @param productId
     * @return
     */
    R removeById(Integer productId);
}
