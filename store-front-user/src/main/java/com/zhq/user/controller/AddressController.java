package com.zhq.user.controller;

import com.zhq.param.AddressListParam;
import com.zhq.param.AddressParam;
import com.zhq.param.AddressRemoveParam;
import com.zhq.pojo.Address;
import com.zhq.user.servcie.AddressService;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 地址的控制层
 */
@RestController
@RequestMapping("/user/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 地址查询的业务
     *
     * @param addressListParam
     * @param result
     * @return
     */
    @PostMapping("list")
    public R list(@RequestBody @Validated AddressListParam addressListParam, BindingResult result) {
        if (result.hasErrors()) {

            return R.fail("参数异常，查询失败!");
        }

        return addressService.list(addressListParam.getUserId());
    }

    /**
     * 地址添加的业务
     *
     * @param
     * @param result
     * @return
     */
    @PostMapping("save")
    public R save(@RequestBody @Validated AddressParam addressParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常，保存失败!");
        }
        Address address = addressParam.getAdd();
        address.setUserId(addressParam.getUserId());

        return addressService.save(address);
    }

    /**
     * 地址移除业务
     *
     * @param addressRemoveParam
     * @param result
     * @return
     */
    @PostMapping("remove")
    public R remove(@RequestBody @Validated AddressRemoveParam addressRemoveParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常,删除失败!");
        }
        return addressService.remove(addressRemoveParam);
    }

}
