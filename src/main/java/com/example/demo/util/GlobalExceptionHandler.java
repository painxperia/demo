package com.example.demo.util;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhangjun
 * @date 2021/7/22  17:25
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public String getMessage(RuntimeException e){
        return e.getMessage();
    }
}
