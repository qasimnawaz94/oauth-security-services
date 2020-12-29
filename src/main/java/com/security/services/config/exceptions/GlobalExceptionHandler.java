package com.security.services.config.exceptions;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);


    private static final String SERVER_ERROR_PLEASE_CHECK_WITH_ADMIN = "Unknown Internal Server Error";
    private static final String OPERATION_FAILED = " Failed to perform operation";
    private static final String REST_TEMPLATE_TIMEOUT = " Access Timeout ";


    private final List<Integer> IGNORE_STACK_TRACE =
            Arrays.asList(ErrorCodes.ERROR_RECORD_NOT_FOUND_EXCEPTION, ErrorCodes.ERROR_DUPLICATE_ENTRY);

    private void logException(String message, StringBuffer url, Exception e) {
        logger.error("********************** Start Global Exception **************");
        logger.error(String.format("URL: {}", url));
        logger.error("********************** End Global Exception **************");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponse> handleError404(HttpServletRequest request, Exception e) {
        ExceptionResponse obj =
                ExceptionResponse.of(ErrorCodes.ERROR_ENDPOINT_NOT_FOUND, "End point not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(obj, obj.getStatus());
    }

    private boolean isStackTraceIgnorable(int code) {
        return IGNORE_STACK_TRACE.contains(code);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ExceptionResponse>
        nullPointerExceptionHandler(HttpServletRequest req, HttpServletResponse response, NullPointerException e)
                throws Exception {

        logException(e.getMessage(), req.getRequestURL(), e);
        String message = Objects.nonNull(e.getMessage()) ? e.getMessage() : SERVER_ERROR_PLEASE_CHECK_WITH_ADMIN;
        ExceptionResponse obj = ExceptionResponse.of(ErrorCodes.INTERNAL_SERVER_ERROR, message, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(obj, obj.getStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponse> defaultErrorHandler(HttpServletRequest req, HttpServletResponse response,
                                                                 Exception e)
            throws Exception {

        ExceptionResponse obj = ExceptionDecorator.create(e);
        logException(e.getMessage(), req.getRequestURL(), e);

        return new ResponseEntity<>(obj, obj.getStatus());
    }


    @ExceptionHandler({InvalidFormatException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ExceptionResponse> defaultErrorHandler(HttpServletRequest req, HttpServletResponse response,
                                                                 InvalidFormatException e)
            throws Exception {

        ExceptionResponse obj = ExceptionDecorator.create(e);
        logException(e.getMessage(), req.getRequestURL(), e);

        return new ResponseEntity<>(obj, obj.getStatus());
    }

    @ExceptionHandler(value = JpaSystemException.class)
    public ResponseEntity<ExceptionResponse>
        handleJpaLockingFailedException(HttpServletRequest req, HttpServletResponse response, JpaSystemException e) {

        logException("Locking failed", req.getRequestURL(), e);

        ExceptionResponse obj = ExceptionResponse.of(ErrorCodes.OPERATION_FAILED, OPERATION_FAILED);

        return new ResponseEntity<>(obj, obj.getStatus());
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponse> handleMethodValidationException(HttpServletRequest req,
                                                                             HttpServletResponse response,
                                                                             MethodArgumentNotValidException ex) {
        String separator = " - ";
        ExceptionResponse obj;
        if (ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage().indexOf(separator) > 0) {
            String messages[] = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage().split(" - ");
            obj = ExceptionResponse.of(Integer.valueOf(messages[0]), messages[1], HttpStatus.BAD_REQUEST);
        }
        else {
            String messages = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
            obj = ExceptionResponse.of(ErrorCodes.INVALID_INPUT, messages, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(obj, obj.getStatus());
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ExceptionResponse>
        handleSpringBindingException(HttpServletRequest req, HttpServletResponse response, BindException ex) {
        String separator = " - ";
        ExceptionResponse obj;
        if (ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage().indexOf(separator) > 0) {
            String messages[] = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage().split(" - ");
            obj = ExceptionResponse.of(Integer.valueOf(messages[0]), messages[1], HttpStatus.BAD_REQUEST);
        }
        else {
            String messages = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
            obj = ExceptionResponse.of(ErrorCodes.INVALID_INPUT, messages, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(obj, obj.getStatus());
    }


    @ExceptionHandler(value = ResourceAccessException.class)
    public ResponseEntity<ExceptionResponse> handleConnectionTimeoutException(HttpServletRequest req,
                                                                              HttpServletResponse response,
                                                                              ResourceAccessException ex) {
        logger.error("Resttemplate call timeout", ex);
        ExceptionResponse obj =
                ExceptionResponse.of(ErrorCodes.REST_TEMPLATE_TIMEOUT, REST_TEMPLATE_TIMEOUT, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(obj, obj.getStatus());
    }
}
