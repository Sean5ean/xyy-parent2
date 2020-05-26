package com.xyy.test.controller;

import com.xyy.test.dao.UserRepository;
import com.xyy.test.entity.User;
import com.xyy.test.shiro.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("login")
    public Object login() {
        return "请登录";
    }

    @GetMapping("unauthc")
    public Object unauthc() {
        return "未授权";
    }

    @GetMapping("index2")
    public Object index2() {
        return "授权成功，进入首页";
    }


    @GetMapping("doLogin")
    public Object doLogin(@RequestParam String username, @RequestParam String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            return "password error!";
        } catch (UnknownAccountException uae) {
            return "username error!";
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
        return "SUCCESS";
    }

    @GetMapping("register")
    public Object register(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        passwordHelper.encryptPassword(user);

        userService.save(user);
        return "SUCCESS";
    }
}

