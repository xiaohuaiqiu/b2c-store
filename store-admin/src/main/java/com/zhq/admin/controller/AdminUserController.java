package com.zhq.admin.controller;

import com.zhq.admin.param.AdminUserParam;
import com.zhq.admin.pojo.AdminUser;
import com.zhq.admin.service.AdminUserService;
import com.zhq.pojo.User;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 后台管理用户的登录
 */
@RestController
@RequestMapping
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 用户登录功能的实现
     * @param adminUserParam
     * @param result
     * @param session
     * @return
     */
    @PostMapping("/user/login")
    public R login(@Validated AdminUserParam adminUserParam, BindingResult result, HttpSession session){
        if (result.hasErrors()) {
            return R.fail("账号异常!登录失败，请检查后重试!");
        }
        //验证码校验
        String captcha = (String) session.getAttribute("captcha"); //从session中获取的验证码
        if (!adminUserParam.getVerCode().equalsIgnoreCase(captcha)) { //开始校验
            return R.fail("验证码输入错误!请重新输入!");
        }
        AdminUser adminUser = adminUserService.login(adminUserParam);

        if (adminUser == null) {
            return R.fail("登录失败,账号密码错误!");
        }
        session.setAttribute("userInfo",adminUser); //前端要取 存入session域一份
        return R.ok("登录成功!");
    }

    @GetMapping("/user/logout")
    public R logout(HttpSession session){
        //清空session即可
        session.invalidate(); //杀死session 清空所有session

        return R.ok("成功退出!");

    }
}
