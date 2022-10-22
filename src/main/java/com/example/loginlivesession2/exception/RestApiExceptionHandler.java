package com.example.loginlivesession2.exception;

import com.example.loginlivesession2.global.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {
    @ExceptionHandler(value = { RequestException.class })
    public ResponseDto<Object> handleApiRequestException(RequestException e) {

        return ResponseDto.fails(
                e.getHttpStatus(),
                e.getMessage()
        );
    }

}
