package com.zhq.category.controller;

import com.alibaba.druid.util.StringUtils;
import com.zhq.category.service.CategoryService;
import com.zhq.param.ProductHotParam;
import com.zhq.param.ProductIdsParam;
import com.zhq.param.ProductPromoParam;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 类别控制类
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据类别名称查询类别对象
     * @param categoryName
     * @return
     */
    @GetMapping("/promo/{categoryName}")
    public R byName(@PathVariable String categoryName) {
        if (StringUtils.isEmpty(categoryName)) {
            return R.fail("类别名称为空,无法查询");
        }
        return categoryService.byName(categoryName);
    }

    /**
     * 根据转入的热门商品集合展示
     */
    @PostMapping("/hots")
    public R hotsCategory(@RequestBody @Validated ProductHotParam productHotParam , BindingResult result){
        if (result.hasErrors()){
            return R.fail("查询失败，参数异常!");
        }
        return categoryService.hots(productHotParam);
    }

    /**
     * 类别数据
     * @return
     */
    @GetMapping("/list")
    public R list(){
        return categoryService.list();
    }

}
