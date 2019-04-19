package com.soaring.eagle.common.shiro;

import com.soaring.eagle.common.shiro.entity.IUser;
import com.soaring.eagle.common.shiro.exception.MustPasswordException;
import com.soaring.eagle.common.shiro.exception.MustUsernameException;
import com.soaring.eagle.common.shiro.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-21
 * Time: 22:55
 * Description:
 */
public abstract class AbstractAuthorizingRealm extends AuthorizingRealm {

    private static final String OR_OPERATOR = " or ";
    private static final String AND_OPERATOR = " and ";
    private static final String NOT_OPERATOR = "not ";

    @Resource
    private IUserService userService;

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 获取用户权限
     *
     * @param user
     * @return
     */
    protected abstract Set<String> findPermissions(IUser user);

    /**
     * 获取用户角色
     *
     * @param user
     * @return
     */
    protected abstract Set<String> findRoles(IUser user);

    /**
     * 获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取当前登录用户的详细信息
        Object primaryPrincipal = principals.getPrimaryPrincipal();
        if (primaryPrincipal == null) {
            return null;
        }

        IUser user = (IUser) primaryPrincipal;

        //为当前用户设置角色和权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(findPermissions(user));
        info.setRoles(findRoles(user));

        return info;
    }

    protected AuthenticationInfo assertAuthenticationInfo(IUser user) {
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return authenticationInfo;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        char[] password = usernamePasswordToken.getPassword();
        if (StringUtils.isBlank(username)) {
            throw new MustUsernameException();
        }
        if (password == null || password.length == 0) {
            throw new MustPasswordException();
        }
        IUser user = userService.findUserByUsername(username);
        if (user == null) {
            throw new UnknownAccountException();
        }

        if (1 == user.getLocked()) {
            throw new LockedAccountException();
        }

        if (1 == user.getDisabled()) {
            throw new DisabledAccountException();
        }
        // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
        return assertAuthenticationInfo(user);
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        CredentialsMatcher cm = getCredentialsMatcher();
        if (cm != null) {
            if (!cm.doCredentialsMatch(token, info)) {
                throw new IncorrectCredentialsException();
            }
        } else {
            throw new AuthenticationException("A CredentialsMatcher must be configured in order to verify "
                    + "credentials during authentication.  If you do not wish for credentials to be examined, you "
                    + "can configure an " + AllowAllCredentialsMatcher.class.getName() + " instance.");
        }
    }

    private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
        if (permission.startsWith(NOT_OPERATOR)) {
            return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
        } else {
            return super.isPermitted(principals, permission);
        }
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        if (permission.contains(OR_OPERATOR)) {
            String[] permissions = permission.split(OR_OPERATOR);
            for (String orPermission : permissions) {
                if (isPermittedWithNotOperator(principals, orPermission)) {
                    return true;
                }
            }
            return false;
        } else if (permission.contains(AND_OPERATOR)) {
            String[] permissions = permission.split(AND_OPERATOR);
            for (String orPermission : permissions) {
                if (!isPermittedWithNotOperator(principals, orPermission)) {
                    return false;
                }
            }
            return true;
        } else {
            return isPermittedWithNotOperator(principals, permission);
        }
    }
}
