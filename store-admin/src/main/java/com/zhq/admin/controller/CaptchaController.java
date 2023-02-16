package com.zhq.admin.controller;

import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码对应得controller
 */
@Controller
@RequestMapping
public class CaptchaController {
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /**
         * 会自动生成一个验证码图片 写回！
         * 并且:将验证码图片存储到session中  验证码默认四个字母
         */
        CaptchaUtil.out(request, response);
    }
}
