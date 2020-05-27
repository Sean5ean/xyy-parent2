package com.xyy.test.shiro;

import com.xyy.test.dao.UserRepository;
import com.xyy.test.entity.SysPermission;
import com.xyy.test.entity.SysRole;
import com.xyy.test.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Optional;

//shiro
public class EnceladusShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserRepository userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) principals.getPrimaryPrincipal();

        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<Object> userName = root.get("username");
                Predicate predicate = criteriaBuilder.equal(userName, username);
                return predicate;
            }
        };

        Optional<User> user = userService.findOne(specification);

        for (SysRole role : user.get().getRoles()) {
            authorizationInfo.addRole(role.getRole());
            for (SysPermission permission : role.getPermissions()) {
                authorizationInfo.addStringPermission(permission.getName());
            }
        }
        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();


        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<Object> userName = root.get("username");
                Predicate predicate = criteriaBuilder.equal(userName, username);
                return predicate;
            }
        };

        Optional<User> user = userService.findOne(specification);

        if (!user.isPresent()) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.get().getUsername(), user.get().getPassword(),
                ByteSource.Util.bytes(user.get().getCredentialsSalt()), getName());
        return authenticationInfo;
    }

}
