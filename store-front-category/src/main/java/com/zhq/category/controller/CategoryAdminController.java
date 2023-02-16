package com.zhq.category.controller;

import com.zhq.category.service.CategoryService;
import com.zhq.param.PageParam;
import com.zhq.pojo.Category;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理分类处理的controller
 */
@RestController
@RequestMapping("/category")
public class CategoryAdminController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 后台服务展示类别接口
     *
     * @param pageParam
     * @return
     */
    @PostMapping("/admin/list")
    public R listPage(@RequestBody PageParam pageParam) {
        return categoryService.listPage(pageParam);
    }

    /**
     * 后台服务添加接类别口
     *
     * @param category
     * @return
     */
    @PostMapping("/admin/save")
    public R adminSave(@RequestBody Category category) {
        return categoryService.adminSave(category);
    }

    /**
     * 后台服务删除类别接口
     *
     * @param categoryId
     * @return
     */
    @PostMapping("/admin/remove")
    public R adminRemove(@RequestBody Integer categoryId) {
        return categoryService.adminRemove(categoryId);
    }

    /**
     * 后台服务修改类别接口
     *
     * @param category
     * @return
     */
    @PostMapping("/admin/update")
    public R adminUpdate(@RequestBody Category category) {
        return categoryService.adminUpdate(category);
    }


}
