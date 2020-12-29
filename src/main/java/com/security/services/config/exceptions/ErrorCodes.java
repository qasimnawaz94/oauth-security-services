package com.security.services.config.exceptions;

public class ErrorCodes {

    public static final int INVALID_ACCESS_TOKEN = 4000;
    public static final int AUTHENTICATION_FAILED = 4001;
    public static final int INVALID_REFRESH_TOKEN_FAILED = 4002;
    public static final int INVALID_GRANT_TYPE = 4003;
    public static final int ACCESS_TOKEN_EXPIRED = 4004;
    public static final int ERROR_INVALID_INPUT = 4005;
    public static final int USER_DISABLED_TEMPORARY = 4006;
    public static final int ERROR_RECORD_NOT_FOUND_EXCEPTION = 4007;

    public static final int ERROR_ENDPOINT_NOT_FOUND = 4008;
    public static final int OPERATION_FAILED = 4009;
    public static final int STALED_OPERATION_FAILED = 4010;
    public static final int REST_TEMPLATE_TIMEOUT = 4011;
    public static final int ERROR_DUPLICATE_ENTRY = 401200;

    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int INVALID_INPUT = 9494;



    private ErrorCodes() {}
}
