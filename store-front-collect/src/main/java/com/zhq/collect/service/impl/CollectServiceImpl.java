package com.zhq.collect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhq.clients.ProductClient;
import com.zhq.collect.mapper.CollectMapper;
import com.zhq.collect.service.CollectService;
import com.zhq.param.ProductCollectParam;
import com.zhq.pojo.Collect;
import com.zhq.pojo.Order;
import com.zhq.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private ProductClient productClient;

    /**
     * 收藏添加的方法
     * 1. 判断是否存在收藏
     * 2. 存在，提示对应的错误
     * 3. 不存在，添加，并且提示添加成功即可
     *
     * @param collect
     * @return
     */
    @Override
    public R save(Collect collect) {
//        1. 判断是否存在收藏
        QueryWrapper<Collect> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", collect.userId);
        queryWrapper.eq("product_id", collect.productId);
        Long count = collectMapper.selectCount(queryWrapper);
//        2. 存在，提示对应的错误
        if (count > 0) {
            return R.fail("您已经收藏!收藏失败!");
        }
//        3. 不存在，添加，并且提示添加成功即可
        //补充时间
        collect.setCollectTime(System.currentTimeMillis());
        int insert = collectMapper.insert(collect);

        return R.ok(insert);

    }

    /**
     * 根据用户id查询商品集合
     * 收藏展示功能方法
     *
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {
        //根据用户id查询该用户收藏的全部商品
        QueryWrapper<Collect> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.select("product_id");
        //查询单列 商品列
        List<Object> ids = collectMapper.selectObjs(queryWrapper);//查询单个列

        ProductCollectParam productCollectParam = new ProductCollectParam();

        List<Integer> Ids = new ArrayList<>(); //进行强制类型转化一下
        for (Object o : ids) {
            Ids.add((Integer) o);
        }
        //转入商品id的集合
        productCollectParam.setProductIds(Ids);
        //调用客户端的方法 展示对应商品ids的详细信息
        R r = productClient.productIds(productCollectParam);

        return r;
    }

    /**
     * 根据用户id和商品id删除商品
     *
     * @param collect
     * @return
     */
    @Override
    public R remove(Collect collect) {
        //校验用户id和商品id
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", collect.userId);
        queryWrapper.eq("product_id", collect.productId);
        //执行方法
        int delete = collectMapper.delete(queryWrapper);

        return R.ok("删除成功!", delete);
    }

    /**
     * 根据商品id删除数据
     *
     * @param productId
     * @return
     */
    @Override
    public R removeById(Integer productId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        int delete = collectMapper.delete(queryWrapper);
        return R.ok("已删除对应商品的用户收藏", delete);
    }
}
