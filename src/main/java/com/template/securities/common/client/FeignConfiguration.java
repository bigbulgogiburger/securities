package com.template.securities.common.client;


import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign client 설정
 * OkHttpClient를 쓰는 이유
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public OkHttpClient client(){
        return new OkHttpClient();
    }
}
