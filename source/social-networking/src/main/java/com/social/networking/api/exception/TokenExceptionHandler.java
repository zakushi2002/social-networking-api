package com.social.networking.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenExceptionHandler extends RuntimeException {
    public TokenExceptionHandler(String exception) {
        super(exception);
    }
}
