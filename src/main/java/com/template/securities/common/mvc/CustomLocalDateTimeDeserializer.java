package com.template.securities.common.mvc;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


/**
 * Json serialized Body를 deserialize 하는 과정에서
 * 응답값을 LocalDateTime Type으로 받으려할 때에, 해당 deserializer를 거친다.
 */
public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {


    public static final ZoneOffset KOREAN_ZONE_OFFSET = ZoneOffset.of("+09:00");

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_ZONE_HEADER_NAME = "time-zone";

    private final String dateTimeFormat;

    private final String zoneHeaderName;

    public CustomLocalDateTimeDeserializer() {
        this.dateTimeFormat = DEFAULT_DATE_TIME_FORMAT;
        this.zoneHeaderName = DEFAULT_ZONE_HEADER_NAME;
    }

    public CustomLocalDateTimeDeserializer(String dateTimeFormat, String zoneHeaderName) {
        this.dateTimeFormat = dateTimeFormat;
        this.zoneHeaderName = zoneHeaderName;
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        LocalDateTime parsedDateTime = LocalDateTime.parse(p.getText(), DateTimeFormatter.ofPattern(dateTimeFormat, LocaleContextHolder.getLocale()));

        ZonedDateTime zonedDateTime = timeZoneFromRequestHeader().map(x -> parsedDateTime.atZone(ZoneId.of(x)))
                .orElse(parsedDateTime.atZone(KOREAN_ZONE_OFFSET));
        if(zonedDateTime.getZone().equals(KOREAN_ZONE_OFFSET)){
            return zonedDateTime.toLocalDateTime();
        }else{
            return zonedDateTime.withZoneSameInstant(KOREAN_ZONE_OFFSET).toLocalDateTime();
        }

    }
    private Optional<String> timeZoneFromRequestHeader(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes instanceof ServletRequestAttributes){
            return timeZoneFromRequestHeader((ServletRequestAttributes) requestAttributes);
        }else{
            return Optional.empty();
        }
    }

    private Optional<String> timeZoneFromRequestHeader(ServletRequestAttributes requestAttributes) {
        HttpServletRequest request = requestAttributes.getRequest();
        return Optional.ofNullable(request.getHeader(zoneHeaderName));
    }

}
