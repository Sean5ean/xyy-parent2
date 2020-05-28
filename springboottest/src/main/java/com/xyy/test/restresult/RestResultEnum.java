package com.xyy.test.restresult;


public enum RestResultEnum {
    //正数是按照正常流程设想以内的不存在异常、错误
    ERROR(-500, "服务器异常"),
    SUCCESS(0, "成功"),
    SUCCESS_DATA(1, "返回请求成功数据"),
    TOAST_SUCCESS(100, "提示信息（成功前端需要展示）"),
    TOAST_FAIL(-100, "提示信息（失败前端需要展示）"),
    TOAST_CHECK(-101, "校验不通过信息"),
    UNAUTH(-401, "没有认证"),
    NOPERMS(-403, "没有权限"),
    LACK_PARAM(-402, "前端缺少参数"),
    ;
    private Integer code;
    private String msg;

    RestResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}