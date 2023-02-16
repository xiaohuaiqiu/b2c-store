package com.zhq.statics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 静态资源的服务启动类
 */
@SpringBootApplication
public class StaticApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaticApplication.class,args);
    }
}
