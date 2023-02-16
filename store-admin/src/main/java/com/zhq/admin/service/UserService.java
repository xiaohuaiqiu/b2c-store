package com.zhq.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhq.param.CartListParam;
import com.zhq.param.PageParam;
import com.zhq.pojo.User;
import com.zhq.utils.R;

public interface UserService {
    /**
     * 用户的展示业务的方法
     *
     * @param pageParam
     * @return
     */
    R userList(PageParam pageParam);

    /**
     * 用户删除业务的方法
     *
     * @param cartListParam
     * @return
     */
    R userRemove(CartListParam cartListParam);

    /**
     * 用户修改业务的方法
     *
     * @param user
     * @return
     */
    R userUpdate(User user);

    /**
     * 用户添加业务的方法
     * @param user
     * @return
     */
    R userSave(User user);
}
