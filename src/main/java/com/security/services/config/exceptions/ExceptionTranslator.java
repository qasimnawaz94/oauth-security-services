package com.security.services.config.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;


@Component
public class ExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    /**
     * Modify OAuth2.0 Error Response
     *
     * @param e
     * @return ResponseEntity<OAuth2Exception>
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public ResponseEntity translate(Exception e) throws Exception {

        ExceptionResponse response = ExceptionDecorator.create(e);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
