package com.xyy.test.exception;

/**
 * 自定义异常,运行时异常不需要处理，会被GlabalExceptionHandler捕获
 */
public class MessageException extends RuntimeException {

    private int status;

    //定义有参构造方法
    public MessageException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
