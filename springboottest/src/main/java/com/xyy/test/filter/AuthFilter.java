package com.xyy.test.filter;


import com.xyy.test.exception.MessageException;
import com.xyy.test.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//jwt
@Component("authFilter")
public class AuthFilter extends FormAuthenticationFilter {

    @Autowired
    JwtUtil jwtUtil;

    /**
     * 判断token是否为空、过期
     * 1. 返回true，shiro就直接允许访问url
     * 2. 返回false，shiro才会根据onAccessDenied的方法的返回值决定是否允许访问url
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String token = getRequestToken((HttpServletRequest) request);
        if (null == token) {
            return false;
        }
        if (StringUtils.isBlank(token)) {
            throw new MessageException(jwtUtil.getHeader() + "不能为空", HttpServletResponse.SC_UNAUTHORIZED);
        }
        Claims claims = jwtUtil.parseToken(token);
        if (null == claims || jwtUtil.isTokenExpired(claims.getExpiration())) {
            throw new MessageException(jwtUtil.getHeader() + "token过期", HttpServletResponse.SC_UNAUTHORIZED);
        }
        return true;
    }

    /**
     * 返回结果为true表明 通过放行允许访问
     * 上面的方法如果返回false,则接下来会执行这个方法,如果返回为true,则不会执行这个方法
     * 判断是否为登录url,进一步判断请求是不是post
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) { //POST
                return true;
            }
        }
        return false;
    }

    /**
     * 获取请求中的token,首先从请求头中获取,如果没有,则尝试从请求参数中获取
     */
    private String getRequestToken(HttpServletRequest request) {
        String token = request.getHeader(jwtUtil.getHeader());
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(jwtUtil.getHeader());
        }
        return token;
    }
}