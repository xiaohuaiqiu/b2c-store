package com.zhq.user.servcie.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhq.param.AddressRemoveParam;
import com.zhq.pojo.Address;
import com.zhq.user.mapper.AddressMapper;
import com.zhq.user.servcie.AddressService;
import com.zhq.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 根据用户id查询 地址数据
     * 1.直接进行数据库查询
     * 2.封装返回结果
     *
     * @param userId 用户id已经校验完毕不为空
     * @return 001 004
     */
    @Override
    public R list(Integer userId) {
        //1.数据库查询
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Address> addressList = addressMapper.selectList(queryWrapper);

        //2.封装返回结果
        R ok = R.ok("查询成功", addressList);
        log.info("AddressService.list业务结束，结果:{}", ok);
        return ok;
    }

    /**
     * 插入地址数据 成功返回新的数据集合
     *
     * @param address 地址数据已经校验完毕
     * @return 返回数据
     */
    @Override
    public R save(Address address) {
        //1.插入数据
        int insert = addressMapper.insert(address);
        //失败
        if (insert == 0) {
            log.info("AddressService.save业务结束，结果:{}", "地址失败!");
            return R.fail("插入地址失败!");
        }
        //2.返回成功数据的集合
        //复用查询业务
        return list(address.getUserId());
    }

    /**
     * 根据id删除地址
     *
     * @param addressRemoveParam
     * @return 001 004
     */
    @Override
    public R remove(AddressRemoveParam addressRemoveParam) {
       //删除
        int id = addressMapper.deleteById(addressRemoveParam.getId());

        if (id == 0){
            log.info("AddressService.remove业务结束，结果:{}", "地址失败!");
            return R.fail("删除地址失败!");
        }
        log.info("AddressService.remove业务结束，结果:{}", "成功!");
        return R.ok("地址删除成功!");
    }
}
