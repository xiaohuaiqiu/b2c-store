package com.zhq.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhq.admin.mapper.AdminUserMapper;
import com.zhq.admin.param.AdminUserParam;
import com.zhq.admin.pojo.AdminUser;
import com.zhq.admin.service.AdminUserService;
import com.zhq.constants.UserConstants;
import com.zhq.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    /**
     * 用户登录校验
     *
     * @param adminUserParam
     * @return
     */
    @Override
    public AdminUser login(AdminUserParam adminUserParam) {
        //校验参数
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", adminUserParam.getUserAccount());
        queryWrapper.eq("user_password", MD5Util.encode(adminUserParam.getUserPassword() +
                UserConstants.USER_SLAT)); //加盐密码
        AdminUser adminUser = adminUserMapper.selectOne(queryWrapper);
        return adminUser;
    }
}
