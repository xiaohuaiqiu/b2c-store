package com.zhq.user.servcie;

import com.zhq.param.AddressRemoveParam;
import com.zhq.pojo.Address;
import com.zhq.utils.R;

public interface AddressService {

    /**
     * 根据用户id查询 地址数据
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 插入地址数据 成功返回新的数据集合
     * @param address 地址数据已经校验完毕
     * @return 返回数据
     */
    R save(Address address);

    /**
     * 根据id删除地址
     * @param addressRemoveParam
     * @return 001 004
     */
    R remove(AddressRemoveParam addressRemoveParam);

}
