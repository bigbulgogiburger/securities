package com.template.securities.common.mvc;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.template.securities.common.mvc.interceptor.RequestLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * WebMvcConfigurer를 상속받아서 mvc 구조를 configure한다.
 * configuration이 있으면, component scan에서 추적되며,
 * 내부 메소드에 @Bean이 있으면 Spring bean으로 등록되게 된다.
 *
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public ObjectMapper objectMapper(){
        //time module 정의
        Module javaTimeModule = new JavaTimeModule()
                // 만들어놓은 deserializer 등록
                .addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer())
                // 만들어놓은 serializer 등록
                .addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer())
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_DATE))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_DATE))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm")))
                .addSerializer(LocalTime.class,new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm")));

        return new ObjectMapper()
                .registerModule(javaTimeModule)
                //naming strategy 등록
                //deserialize시에 namingStrategy를 어떻게 잡을 것인가(snake -> camel)
                //serialize시에 naingStrategy (camel->snake)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                //모르는 값이 올때에 익셉션으로 던질것인가? -> x
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 개발한 인터셉터를 등록하고, 모든 경로에서 적용한다.
        registry.addInterceptor(new RequestLoggingInterceptor()).addPathPatterns("/**");
    }


}
