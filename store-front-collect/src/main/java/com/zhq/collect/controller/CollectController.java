package com.zhq.collect.controller;

import com.zhq.collect.service.CollectService;
import com.zhq.pojo.Collect;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收藏控制层
 */
@RestController
@RequestMapping("collect")
public class CollectController {

    @Autowired
    private CollectService collectService; //业务接口

    /**
     * 添加收藏的功能
     *
     * @param collect
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody Collect collect) {
        return collectService.save(collect);
    }

    /**
     * 收藏商品展示功能实现
     *
     * @param collect
     * @return
     */
    @PostMapping("/list")
    public R list(@RequestBody Collect collect) {
        return collectService.list(collect.getUserId());
    }

    /**
     * 删除用户收藏的商品
     *
     * @param collect
     * @return
     */
    @PostMapping("/remove")
    public R remove(@RequestBody Collect collect) {
        return collectService.remove(collect);
    }

    /**
     * 后台管理删除商品之对应的收藏也随之删除!
     *
     * @param productId
     * @return
     */
    @PostMapping("/remove/product")
    public R removeById(@RequestBody Integer productId) {
        return collectService.removeById(productId);
    }
}
