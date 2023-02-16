package com.zhq.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhq.constants.UserConstants;
import com.zhq.param.CartListParam;
import com.zhq.param.PageParam;
import com.zhq.pojo.User;
import com.zhq.user.mapper.UserMapper;
import com.zhq.user.servcie.UserService;
import com.zhq.utils.MD5Util;
import com.zhq.utils.R;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 被后台管理调用的接口
 */
@RestController
@RequestMapping("user")
public class UserAdminController {
    @Autowired
    private UserService userService;

    /**
     * 后台管理系统的用户展示
     *
     * @param param
     * @return
     */
    @PostMapping("admin/list")
    public R listPage(@RequestBody PageParam param) {
        return userService.listPage(param);
    }

    /**
     * 后台管理系统的用户删除
     *
     * @param cartListParam
     * @return
     */
    @PostMapping("admin/remove")
    public R remove(@RequestBody CartListParam cartListParam) { //复用一下里面的user_id
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cartListParam.getUserId());
        boolean remove = userService.remove(queryWrapper);
        return R.ok("删除成功!", remove);
    }

    /**
     * 后台管理系统的用户修改
     *
     * @param user
     * @return
     */
    @PostMapping("admin/update")
    public R update(@RequestBody User user) { //复用一下里面的user_id
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getUserId());
        queryWrapper.eq("password", user.getPassword());
        long count = userService.count(queryWrapper);
        if (count == 0) {
            //证明修改 加密
            user.setPassword(MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT));
        }
        boolean update = userService.updateById(user);
        return R.ok("修改成功!", update);
    }

    /**
     * 后台管理系统的用户添加
     *
     * @param user
     * @return
     */
    @PostMapping("admin/save")
    public R save(@RequestBody User user) { //复用一下里面的user_id
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());
        //数据库查询一下
        long count = userService.count(queryWrapper);
        if (count == 0) {
            String newPwd = MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT);
            user.setPassword(newPwd);
            //说明是新用户
            boolean save = userService.save(user);
            return R.ok("添加成功!", save);
        }

        return R.fail("账号存在添加失败!");
    }
}
