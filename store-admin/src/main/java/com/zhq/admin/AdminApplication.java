package com.zhq.admin;

import com.zhq.clients.*;
import com.zhq.pojo.Category;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = "com.zhq.admin.mapper")
@SpringBootApplication
@EnableCaching //开启缓存支持
@EnableFeignClients(clients = {UserClient.class, CategoryClient.class, SearchClient.class, ProductClient.class, OrderClient.class})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
