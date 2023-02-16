package com.zhq.admin.inteceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录保护拦截器
 * 进来的都是要拦截的
 * 检查在session中是否有数据 userInfo 有 放行! 无 跳转到登录页面
 */
@Component //放入ioc容器
public class LoginProtectInterceptor implements HandlerInterceptor { //HandlerInterceptor 由springmvc提供

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object userInfo = request.getSession().getAttribute("userInfo"); //检查在session中是否有数据 userInfo 有 放行! 无 跳转到登录页面

        if (userInfo != null) {
            //放行!
            return true;
        } else {
            //拦截 false
            response.sendRedirect(request.getContextPath() + "/index.html"); //拦截并且返回登录页面
            return false;
        }

    }
}
