package com.zhq.carousel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhq.carousel.mapper.CarouselMapper;
import com.zhq.carousel.service.CarouselService;
import com.zhq.pojo.Carousel;
import com.zhq.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * 查询优先级最高的6条数据
     * 按照优先级查询数据库数据
     * 用stream流 进行内存切割
     *
     * @return
     */
    @Cacheable(value = "list.carousel",key = "#root.methodName",cacheManager = "cacheManagerDay")
    @Override
    public R list() {
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("priority");

        List<Carousel> carousels = carouselMapper.selectList(queryWrapper);

        //jdk 1.8新特性
        List<Carousel> collect = carousels.stream().limit(6).collect(Collectors.toList());
        R ok = R.ok(collect);

        log.info("CarouselServiceImpl.list业务结束，结果是：{}", ok);
        return ok;
    }
}
