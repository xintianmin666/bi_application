package com.yiyun.yiyuncarservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyPicConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 服务器目录
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/ytCarService/upload/");

        // 本地目录
//        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/ytCarService/upload/");
    }
}
