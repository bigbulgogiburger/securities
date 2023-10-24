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
