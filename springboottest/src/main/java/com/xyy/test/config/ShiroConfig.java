package com.xyy.test.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.xyy.test.filter.AuthFilter;
import com.xyy.test.shiro.EnceladusShiroRealm;
import com.xyy.test.shiro.PasswordHelper;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//shiro
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager, AuthFilter authFilter) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        shiroFilter.setLoginUrl("/login"); //认证不通过的页面
        shiroFilter.setUnauthorizedUrl("/unauthc"); //授权不通过的页面
        shiroFilter.setSuccessUrl("/index2");

        //设置自定义filter
        //Map<String, Filter> filter = new HashedMap();
        //filter.put("auth", authFilter); //@Component("authFilter") 所以这里可以注入
        //shiroFilter.setFilters(filter);

        //使用LinkedHashMap保证拦截器的顺序性
        Map<String, String> filterRuleMap = new LinkedHashMap<>();


        filterRuleMap.put("/doLogin", "anon"); //对登录放行
        filterRuleMap.put("/login", "anon");
        filterRuleMap.put("/unauthc", "anon");
        filterRuleMap.put("/authc/token", "authc"); //自定义的filter
        filterRuleMap.put("/authc/index", "authc");
        filterRuleMap.put("/authc/admin", "roles[admin]");
        filterRuleMap.put("/authc/renewable", "perms[Create,Update]");
        filterRuleMap.put("/authc/removable", "perms[Delete]");
        //filterRuleMap.put("/**", "auth");
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilter;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME); // 散列算法
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.HASH_ITERATIONS); // 散列次数
        return hashedCredentialsMatcher;
    }

    @Bean
    public EnceladusShiroRealm shiroRealm() {
        EnceladusShiroRealm shiroRealm = new EnceladusShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher()); // 原来在这里
        return shiroRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public PasswordHelper passwordHelper() {
        return new PasswordHelper();
    }
}