package com.security.services.config.exceptions;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private Integer code;
    private String message;
    private HttpStatus status;

    public static ExceptionResponse of(Integer code, String message) {
        return new ExceptionResponse(code, message, HttpStatus.BAD_REQUEST);
    }

    public static ExceptionResponse of(Integer code, String message, HttpStatus status) {
        return new ExceptionResponse(code, message, status);
    }

    public static ExceptionResponse of(Integer code, String message, int statusCode) {
        return new ExceptionResponse(code, message, HttpStatus.valueOf(statusCode));
    }
}
