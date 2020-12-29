package com.security.services.config.exceptions;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;


public class ExceptionDecorator {

    private ExceptionDecorator() {}

    private static final String INVALID_ACCESS_TOKEN = "Invalid access token";
    private static final String INVALID_GRANT_TYPE = "Unsupported grant type";
    private static final String BAD_CREDENTIALS = "Bad credentials";
    private static final String INVALID_REFRESH_TOKEN = "Invalid refresh toke";
    private static final String ACCESS_TOKEN_EXPIRED = "Access token expired";
    private static final String USER_DISABLED = "User is disabled";

    public static ExceptionResponse create(Exception e) {

        int code = ErrorCodes.ERROR_INVALID_INPUT;
        HttpStatus httpCode = HttpStatus.BAD_REQUEST;

        String message = null;
        if (Objects.nonNull(e.getMessage()) && e.getMessage().contains(INVALID_GRANT_TYPE)) {
            code = ErrorCodes.INVALID_GRANT_TYPE;
            message = INVALID_GRANT_TYPE;
            httpCode = HttpStatus.BAD_REQUEST;
        }
        else if (Objects.nonNull(e.getMessage()) && e.getMessage().contains(INVALID_REFRESH_TOKEN)) {
            code = ErrorCodes.INVALID_REFRESH_TOKEN_FAILED;
            message = INVALID_REFRESH_TOKEN;
            httpCode = HttpStatus.UNAUTHORIZED;
        }
        else if (Objects.nonNull(e.getMessage()) && e.getMessage().contains(BAD_CREDENTIALS)) {
            code = ErrorCodes.AUTHENTICATION_FAILED;
            message = INVALID_ACCESS_TOKEN;
            httpCode = HttpStatus.UNAUTHORIZED;
        }
        else if (Objects.nonNull(e.getMessage()) && e.getMessage().contains(INVALID_ACCESS_TOKEN)) {

            code = ErrorCodes.INVALID_ACCESS_TOKEN;
            message = INVALID_ACCESS_TOKEN;
            httpCode = HttpStatus.UNAUTHORIZED;
        }
        else if (Objects.nonNull(e.getMessage()) && e.getMessage().contains(ACCESS_TOKEN_EXPIRED)) {

            code = ErrorCodes.ACCESS_TOKEN_EXPIRED;
            message = ACCESS_TOKEN_EXPIRED;
            httpCode = HttpStatus.UNAUTHORIZED;
        }
        else if (Objects.nonNull(e.getMessage()) && e.getMessage().contains(USER_DISABLED)) {

            code = ErrorCodes.USER_DISABLED_TEMPORARY;
            message = USER_DISABLED;
            httpCode = HttpStatus.FORBIDDEN;
        }

        else if (Objects.nonNull(e.getMessage()) &&
            (e instanceof HttpMessageNotReadableException || e instanceof InvalidFormatException)) {

                code = ErrorCodes.INVALID_INPUT;
                message = e.getMessage();
                httpCode = HttpStatus.BAD_REQUEST;
            }

        if (code == ErrorCodes.ERROR_RECORD_NOT_FOUND_EXCEPTION) {
            httpCode = HttpStatus.NOT_FOUND;
        }

        return ExceptionResponse.of(code, message, httpCode);
    }
}
