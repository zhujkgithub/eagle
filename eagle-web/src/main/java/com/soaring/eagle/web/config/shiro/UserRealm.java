package com.soaring.eagle.web.config.shiro;

import com.soaring.eagle.common.shiro.AbstractAuthorizingRealm;
import com.soaring.eagle.common.shiro.entity.IUser;
import com.soaring.eagle.common.shiro.exception.MustPasswordException;
import com.soaring.eagle.common.shiro.exception.MustUsernameException;
import com.soaring.eagle.entity.user.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/22
 * Time: 13:23
 */
public class UserRealm extends AbstractAuthorizingRealm {

    public UserRealm() {

    }

    /**
     * 获取用户权限
     *
     * @param user
     * @return
     */
    @Override
    protected Set<String> findPermissions(IUser user) {
        return null;
    }

    /**
     * 获取用户角色
     *
     * @param user
     * @return
     */
    @Override
    protected Set<String> findRoles(IUser user) {
        return null;
    }

    //最近项目中用到shiro ，研究了下登录授权中realm 2个方法的调用问题。。这2个方法究竟是在什么时候调用的，先记录如下：

    // shiro 中的AuthorizingRealm有2个方法doGetAuthorizationInfo()和doGetAuthenticationInfo(),
    // 一般实际开发中，我们都继承AuthorizingRealm类然后重写doGetAuthorizationInfo和doGetAuthenticationInfo。
    // doGetAuthenticationInfo这个方法是在用户登录的时候调用的也就是执行SecurityUtils.getSubject().login（）的时候调用；
    // 而doGetAuthorizationInfo方法是在我们调用SecurityUtils.getSubject().isPermitted（）这个方法时会调用doGetAuthorizationInfo（），
    // 一而我们的@RequiresPermissions这个注解起始就是在执行SecurityUtils.getSubject().isPermitted（）。
    // 我们在某个方法上加上@RequiresPermissions这个，那么我们访问这个方法的时候，
    // 就会自动调用SecurityUtils.getSubject().isPermitted（），从而区调用doGetAuthorizationInfo 匹配

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

    @Override
    protected AuthenticationInfo assertAuthenticationInfo(IUser user) {
        if (user == null) {
            return null;
        }
        User accUser = (User) user;
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(accUser, accUser.getPassword(), getName());
        return authenticationInfo;
    }

    /**
     * 登录认证
     *
     * @param token
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        String username = upt.getUsername();
        char[] password = upt.getPassword();
        if (StringUtils.isBlank(username)) {
            throw new MustUsernameException();
        }

        if (password == null || password.length == 0) {
            throw new MustPasswordException();
        }

        IUser user = getUserService().findUserByUsername(username);
        if (user == null) {
            throw new UnknownAccountException();
        }

        if (1 == user.getLocked()) {
            throw new LockedAccountException();
        }

        if (1 == user.getDisabled()) {
            throw new DisabledAccountException();
        }

        AuthenticationInfo authenticationInfo = assertAuthenticationInfo(user);

        return authenticationInfo;
    }
}
