package com.zhq.clients;

import com.zhq.param.PageParam;
import com.zhq.param.ProductHotParam;
import com.zhq.pojo.Category;
import com.zhq.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 类别的调用接口
 */
@FeignClient("category-service")
public interface CategoryClient {

    @GetMapping("/category/promo/{categoryName}")
    R byName(@PathVariable String categoryName);

    @PostMapping("/category/hots")
    R host(@RequestBody ProductHotParam productHotParam);

    @GetMapping("/category/list")
    R list();

    /**
     * 后台管理需要的客户端 显示全部数据
     */
    @PostMapping("/category/admin/list")
    R adminPageList(@RequestBody PageParam pageParam);

    /**
     * 后台管理需要的客户端 添加类别
     */
    @PostMapping("/category/admin/save")
    R adminSave(@RequestBody Category category);

    /**
     * 后台管理需要的客户端 删除类别
     */
    @PostMapping("/category/admin/remove")
    R adminRemove(@RequestBody Integer categoryId);

    /**
     * 后台管理需要的客户端 修改类别
     */
    @PostMapping("/category/admin/update")
    R adminUpdate(@RequestBody Category category);

}
