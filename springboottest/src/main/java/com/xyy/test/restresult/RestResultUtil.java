package com.xyy.test.restresult;


public class RestResultUtil {

    /**
     * 成功且带数据
     **/
    public static RestResult success(Object object) {
        RestResult restResult = new RestResult();
        restResult.setCode(RestResultEnum.SUCCESS.getCode());
        restResult.setMsg(RestResultEnum.SUCCESS.getMsg());
        restResult.setData(object);
        return restResult;
    }

    /**
     * 成功但不带数据
     **/
    public static RestResult success() {
        return success(null);
    }

    /**
     * 失败
     **/
    public static RestResult error(Integer code, String msg) {
        RestResult restResult = new RestResult();
        restResult.setCode(code);
        restResult.setMsg(msg);
        return restResult;
    }


    /**
     * 返回异常
     **/
    public static RestResult exception(Exception e) {
        RestResult restResult = new RestResult();
        restResult.setCode(RestResultEnum.ERROR.getCode());
        restResult.setMsg(RestResultEnum.ERROR.getMsg());
        restResult.setData(e.getMessage());
        return restResult;
    }

    public static RestResult toast(String info) {
        RestResult restResult = new RestResult();
        restResult.setCode(RestResultEnum.TOAST_SUCCESS.getCode());
        restResult.setMsg(info);
        return restResult;
    }
}