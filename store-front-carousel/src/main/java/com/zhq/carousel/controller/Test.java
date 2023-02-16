package com.zhq.carousel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @RequestMapping("/test")
    public String test() {
        String s = "你好世界";
        return s;
    }
}
