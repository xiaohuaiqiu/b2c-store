package com.zhq.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhq.category.mapper.CategoryMapper;
import com.zhq.category.service.CategoryService;
import com.zhq.clients.ProductClient;
import com.zhq.param.PageParam;
import com.zhq.param.ProductHotParam;
import com.zhq.param.ProductIdsParam;
import com.zhq.pojo.Category;
import com.zhq.pojo.Product;
import com.zhq.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductClient productClient;

    /**
     * 根据类别名称查询类别对象
     *
     * @param categoryName
     * @return
     */
    @Override
    public R byName(String categoryName) {
        //封装查询参数
        QueryWrapper<Category> queryWrapper = new QueryWrapper();
        queryWrapper.eq("category_name", categoryName);
        //查询数据库
        Category category = categoryMapper.selectOne(queryWrapper);
        if (category == null) {
            return R.fail("查询失败!");
        }
        //结果封装
        return R.ok("查询成功!", category);
    }

    /**
     * 根据转入的热门商品集合返回类别对应的id
     *
     * @param productHotParam
     * @return
     */
    @Override
    public R hots(ProductHotParam productHotParam) {
        //封装查询参数
        QueryWrapper<Category> queryWrapper = new QueryWrapper();
        queryWrapper.in("category_name", productHotParam.getCategoryName());
        queryWrapper.select("category_id");

        //查询数据库
        List<Object> ids = categoryMapper.selectObjs(queryWrapper);
        R ok = R.ok("类别查询成功!", ids);
        return ok;
    }

    /**
     * 查询类别数据，进行返回
     *
     * @return r 类别数据集合
     */
    @Override
    public R list() {
        List<Category> list = categoryMapper.selectList(null);

        R ok = R.ok("类别集合全部数据查询成功!", list);
        return ok;
    }

    /**
     * 分页查询
     *
     * @param pageParam
     * @return
     */
    @Override
    public R listPage(PageParam pageParam) {
        IPage<Category> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());

        IPage<Category> iPage = categoryMapper.selectPage(page, null);

        List<Category> records = iPage.getRecords();
        long total = iPage.getTotal();
        return R.ok("查询成功", records, total);
    }

    /**
     * 添加类别信息
     *
     * @param category
     * @return
     */
    @Override
    public R adminSave(Category category) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name", category.getCategoryName());
        Long aLong = categoryMapper.selectCount(queryWrapper);
        if (aLong > 0) {
            return R.fail("类别已经存在,添加失败!");
        }
        int insert = categoryMapper.insert(category);
        return R.ok("添加成功!", insert);
    }

    /**
     * 删除类别信息
     *
     * @param categoryId
     * @return
     */
    @Override
    public R adminRemove(Integer categoryId) {
        Long count = productClient.adminCount(categoryId);
        if (count > 0) {
            return R.fail("失败，对应类别还有" + count + "件商品正在引用,不能删除呢!");
        }
        int id = categoryMapper.deleteById(categoryId);
        return R.ok("类别删除成功!", id);
    }

    /**
     * 修改类别信息
     *
     * @param category
     * @return
     */
    @Override
    public R adminUpdate(Category category) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name", category.getCategoryName());
        Long count = categoryMapper.selectCount(queryWrapper);
        if (count > 0) {
            return R.fail("该类别已经存在!修改失败!");
        }
        int update = categoryMapper.updateById(category);
        return R.ok("修改成功!", update);
    }

}
