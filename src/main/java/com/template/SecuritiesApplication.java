package com.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.time.Clock;
import java.time.ZoneId;

/**
 * enablexxx -> 프록시 생성이라고 생각하면 쉽습니다.
 * @EnableWebSecurity : 스프링 시큐리티를 사용할 것이다.
 * @EnableConfigurationProperties : @ConfigurationProperties를 사용할 것이다.
 * @EnableFeignClients : feign을 사용할 것이다.
 * @EnableAspectJAutoProxy : Spring aop를 사용할 것이다.
 * 		ErrorMvcAutoConfiguration.class,
 * 		SecurityAutoConfiguration.class
 * 	위 두클래스를 exclude하여, Springsecurity가 기본으로 작동하지 않게 하고, mvcConfiguration도 자동으로 작동하지 않게 한다.
 */
@EnableWebSecurity
@EnableConfigurationProperties
@EnableFeignClients
@EnableAspectJAutoProxy
@SpringBootApplication(exclude = {
		ErrorMvcAutoConfiguration.class,
		SecurityAutoConfiguration.class})
public class SecuritiesApplication {

	public static final Clock DEFAULT_CLOCK = Clock.systemDefaultZone();
	public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

	public static void main(String[] args) {
		SpringApplication.run(SecuritiesApplication.class, args);
	}

}
