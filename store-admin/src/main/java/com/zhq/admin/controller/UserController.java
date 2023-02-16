package com.zhq.admin.controller;

import com.zhq.admin.service.UserService;
import com.zhq.param.CartListParam;
import com.zhq.param.PageParam;
import com.zhq.pojo.User;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台用户模块管理的controller
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户展示业务
     *
     * @param pageParam
     * @return
     */
    @GetMapping("list")
    public R userList(PageParam pageParam) {
        return userService.userList(pageParam);
    }


    /**
     * 用户删除业务
     *
     * @param cartListParam
     * @return
     */
    @PostMapping("remove")
    public R userRemove(CartListParam cartListParam) {
        return userService.userRemove(cartListParam);
    }

    /**
     * 用户修改业务
     *
     * @param user
     * @return
     */
    @PostMapping("update")
    public R UserUpdate(User user){
        return userService.userUpdate(user);
    }

    /**
     * 用户添加业务
     *
     * @param user
     * @return
     */
    @PostMapping("save")
    public R UserSave(User user){
        return userService.userSave(user);
    }


}
