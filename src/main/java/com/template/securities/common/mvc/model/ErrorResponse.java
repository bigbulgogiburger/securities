package com.template.securities.common.mvc.model;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
public class ErrorResponse {
    private LocalDateTime time;
    private String message;
    private String path;
    private Long resultCode;

    public ErrorResponse(LocalDateTime time, String message, String path) {
        this.time = time;
        this.message = message;
        this.path = path;
    }
}
