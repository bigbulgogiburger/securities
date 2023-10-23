package com.template.securities.common.mvc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    public static final ZoneOffset KOREAN_ZONE_OFFSET = ZoneOffset.of("+09:00");

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_ZONE_HEADER_NAME = "time-zone";

    private final String dateTimeFormat;

    private final String zoneHeaderName;

    public CustomLocalDateTimeSerializer() {
        this.dateTimeFormat = DEFAULT_DATE_TIME_FORMAT;
        this.zoneHeaderName = DEFAULT_ZONE_HEADER_NAME;
    }

    public CustomLocalDateTimeSerializer(String dateTimeFormat, String zoneHeaderName) {
        this.dateTimeFormat = dateTimeFormat;
        this.zoneHeaderName = zoneHeaderName;
    }


    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        ZonedDateTime zonedDateTime = value.atZone(KOREAN_ZONE_OFFSET);

        String dateTime = timeZoneFromRequestHeader().map(x -> zonedDateTime.withZoneSameInstant(ZoneId.of(x)))
                .orElse(zonedDateTime).format(DateTimeFormatter.ofPattern(dateTimeFormat, LocaleContextHolder.getLocale()));

        gen.writeString(dateTime);

    }

    private Optional<String> timeZoneFromRequestHeader() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes instanceof ServletRequestAttributes){
            return timeZoneFromRequestHeader((ServletRequestAttributes) requestAttributes);
        }else{
            return Optional.empty();
        }
    }

    private Optional<String> timeZoneFromRequestHeader(ServletRequestAttributes requestAttributes) {
        return Optional.ofNullable(requestAttributes.getRequest().getHeader(zoneHeaderName));
    }
}
