package com.zhq.admin.service.impl;

import com.zhq.admin.service.CategoryService;
import com.zhq.clients.CategoryClient;
import com.zhq.param.PageParam;
import com.zhq.pojo.Category;
import com.zhq.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryClient categoryClient;

    /**
     * 分页查询的方法
     *
     * @param pageParam
     * @return
     */
    @Cacheable(value = "list.category", key = "#pageParam.currentPage+'-'+#pageParam.pageSize")
    @Override
    public R pageList(PageParam pageParam) {

        R r = categoryClient.adminPageList(pageParam);
        return r;
    }

    /**
     * 插入数据
     *
     * @param category
     * @return
     */
    @CacheEvict(value = "list.category", allEntries = true)
    @Override
    public R save(Category category) {

        R r = categoryClient.adminSave(category);
        return r;
    }

    /**
     * 删除数据
     *
     * @param categoryId
     * @return
     */
    @CacheEvict(value = "list.category", allEntries = true)
    @Override
    public R remove(Integer categoryId) {
        R r = categoryClient.adminRemove(categoryId);
        return r;
    }

    /**
     * 修改数据
     *
     * @param category
     * @return
     */
    @CacheEvict(value = "list.category", allEntries = true)
    @Override
    public R update(Category category) {
        R r = categoryClient.adminUpdate(category);
        return r;
    }
}
