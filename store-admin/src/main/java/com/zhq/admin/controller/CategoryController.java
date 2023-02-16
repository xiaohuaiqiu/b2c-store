package com.zhq.admin.controller;

import com.zhq.admin.service.CategoryService;
import com.zhq.clients.CategoryClient;
import com.zhq.param.PageParam;
import com.zhq.pojo.Category;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类别controller
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 显示类别功能
     *
     * @param pageParam
     * @return
     */
    @GetMapping("/list")
    public R pageList(PageParam pageParam) {
        return categoryService.pageList(pageParam);
    }

    /**
     * 添加类别
     *
     * @param category
     * @return
     */
    @PostMapping("/save")
    public R save(Category category) {
        return categoryService.save(category);
    }

    /**
     * 删除类别
     *
     * @param categoryId
     * @return
     */
    @PostMapping("/remove")
    public R remove(Integer categoryId) {
        return categoryService.remove(categoryId);
    }

    /**
     * 修改类别
     *
     * @param category
     * @return
     */
    @PostMapping("/update")
    public R update(Category category) {
        return categoryService.update(category);
    }
}
