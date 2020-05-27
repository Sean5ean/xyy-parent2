package com.xyy.test.exception;

import com.xyy.test.restresult.RestResult;
import com.xyy.test.restresult.RestResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(value = MessageException.class)
    @ResponseBody
    public RestResult bizExceptionHandler(HttpServletRequest req, MessageException e) {
        log.error("发生业务异常！原因是：{}", e.getMessage());
        return RestResultUtil.exception(e);
    }

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public RestResult exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return RestResultUtil.exception(e);
    }


    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestResult exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常！原因是:", e);
        return RestResultUtil.exception(e);
    }
}
