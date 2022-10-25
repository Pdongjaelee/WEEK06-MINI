package com.example.loginlivesession2.exception;

import com.example.loginlivesession2.global.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {
    @ExceptionHandler(value = { RequestException.class })
    public ResponseEntity<ResponseDto<Object>> handleApiRequestException(RequestException e) {

        return new ResponseEntity<>(ResponseDto.fails(
                e.getHttpStatus(),
                e.getMessage()), e.getHttpStatus()
        );
    }

}
