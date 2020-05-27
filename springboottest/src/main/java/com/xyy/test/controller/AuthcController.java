package com.xyy.test.controller;

import com.xyy.test.entity.User;
import com.xyy.test.exception.CustomException;
import com.xyy.test.restresult.RestResult;
import com.xyy.test.restresult.RestResultEnum;
import com.xyy.test.restresult.RestResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//shiro
@RestController
@RequestMapping("authc")
public class AuthcController {

    @GetMapping("index")
    public RestResult<String> index() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("user");
        return RestResultUtil.success(user);
    }

    @GetMapping("admin")
    public RestResult<String> admin() {
        return RestResultUtil.success("管理页面");
    }

    // delete
    @GetMapping("removable")
    public RestResult<String> removable() {
        return RestResultUtil.success("removable");
    }

    // insert & update
    @GetMapping("renewable")
    public RestResult<String> renewable() {
        return RestResultUtil.success("renewable");
    }

    // token
    @GetMapping("token")
    public Object token() {
        throw new CustomException("tokenl", 345);
        //return "token";
    }
}
