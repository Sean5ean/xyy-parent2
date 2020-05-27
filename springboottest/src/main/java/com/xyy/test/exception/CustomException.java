package com.xyy.test.exception;

import org.springframework.http.HttpStatus;

/**
 * 自定义异常
 */
public class CustomException extends RuntimeException {

    private int status;

    //定义有参构造方法
    public CustomException(String message, int httpStatus) {
        super(message);
        status = httpStatus;
    }

    public int getStatus() {
        return status;
    }
}
