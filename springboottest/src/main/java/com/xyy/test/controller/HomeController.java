package com.xyy.test.controller;

import com.xyy.test.dao.UserRepository;
import com.xyy.test.entity.User;
import com.xyy.test.restresult.RestResult;
import com.xyy.test.restresult.RestResultUtil;
import com.xyy.test.shiro.PasswordHelper;
import com.xyy.test.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import java.util.Optional;

//shiro
@RestController
@RequestMapping
public class HomeController {
    @Autowired
    private UserRepository userService;
    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("login")
    public RestResult<String> login() {
        return RestResultUtil.toast("请登录");
    }

    @GetMapping("unauthc")
    public Object unauthc() {
        return RestResultUtil.error(-403, "未授权");
    }

    @GetMapping("index2")
    public Object index2() {
        return RestResultUtil.success();
    }


    @RequestMapping(value = "doLogin", method = {RequestMethod.GET, RequestMethod.POST})
    public Object doLogin(@RequestParam String username, @RequestParam String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);


        String tokenBack = "";
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            //返回的token
            tokenBack = jwtUtil.generateToken(username);
        } catch (IncorrectCredentialsException ice) {
            return "密码不正确";
        } catch (UnknownAccountException uae) {
            return "不存在该账户";
        }

        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<Object> userName = root.get("username");
                Predicate predicate = criteriaBuilder.equal(userName, username);
                return predicate;
            }
        };
        Optional<User> user = userService.findOne(specification);
        subject.getSession().setAttribute("user", user.get());
        return "登录成功" + tokenBack;
    }

    @GetMapping("register")
    public Object register(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        passwordHelper.encryptPassword(user);

        userService.save(user);
        return "注册成功";
    }
}

