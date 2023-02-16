package com.zhq.admin.service;

import com.zhq.admin.param.AdminUserParam;
import com.zhq.admin.pojo.AdminUser;

public interface AdminUserService {

    /**
     * 用户登录校验
     * @param adminUserParam
     * @return
     */
    AdminUser login(AdminUserParam adminUserParam);
}
