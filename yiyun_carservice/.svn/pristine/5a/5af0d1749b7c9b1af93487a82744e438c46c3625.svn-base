package com.yiyun.yiyuncarservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @author hxx
 * @version 1.0
 * @title: WeatherConfig
 * @projectName ytcxxcx
 * @description: TODO
 * @date 2020/6/20 11:26
 */
public class WeatherConfig {
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    restTemplate
        .getMessageConverters()
        .set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    return restTemplate;
  }
}
