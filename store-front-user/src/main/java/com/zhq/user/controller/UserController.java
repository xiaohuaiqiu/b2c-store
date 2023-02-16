package com.zhq.user.controller;

import com.zhq.param.UserCheckParam;
import com.zhq.param.UserLoginParam;
import com.zhq.pojo.User;
import com.zhq.user.servcie.UserService;
import com.zhq.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户模块的控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userservice;

    /**
     * 检查账号是否可以用的接口
     *
     * @param userCheckParam 接收检查的账号实体 内部有参数校验注解
     * @param result         获取校验结果的实体对象
     * @return Validated注解的作用是让之前加的NotBlank生效
     * BindingResult校验结果 并且必须紧贴UserCheckParam
     */
    @PostMapping("/check")
    public R check(@RequestBody @Validated UserCheckParam userCheckParam, BindingResult result) {  //@RequestBody复习一下 这个注解代表要接收前端json数据类型转换成实体类
        //检查是否是符合校验注解的规则 符合返回false 不符合返回true
        boolean b = result.hasErrors();
        if (b == true){
            return R.fail("账号为空,请您重新认真输入!");
        }
        return userservice.check(userCheckParam);
    }

    /**
     * 注册业务的实现
     * @param user
     * @param result
     * @return
     */
    @PostMapping("/register")
    public R regiater(@RequestBody @Validated User user,BindingResult result){
        if (result.hasErrors()){
            //如果存在异常，证明请求参数不符合注解要求
            return  R.fail("参数异常，不可注册!");
        }
        return userservice.register(user);
    }

    /**
     * 登录业务的实现
     * @param
     * @param result
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody @Validated UserLoginParam userLoginParam, BindingResult result){
        if (result.hasErrors()){
            //如果存在异常，证明请求参数不符合注解要求
            return  R.fail("参数异常，不可登录!");
        }
        return userservice.login(userLoginParam);
    }

}
