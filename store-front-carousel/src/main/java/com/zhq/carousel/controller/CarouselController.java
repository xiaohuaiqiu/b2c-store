package com.zhq.carousel.controller;

import com.zhq.carousel.service.CarouselService;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 轮播图控制层
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    /**
     * 查询轮播图数据 只要优先级最高的6条
     */
    @PostMapping("/list")
    @ResponseBody
    public R list() {
        return carouselService.list();
    }
}
