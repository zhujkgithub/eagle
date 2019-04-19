package com.soaring.eagle.admin.config.shiro;

import com.soaring.eagle.admin.config.shiro.service.impl.UserServiceImpl;
import com.soaring.eagle.common.shiro.KaptchaFormAuthenticationFilter;
import com.soaring.eagle.entity.sys.SysUser;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.support.MessageSourceAccessor;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-22
 * Time: 20:42
 * Description:
 */
public class CustomFormAuthenticationFilter extends KaptchaFormAuthenticationFilter {

    private UserServiceImpl userService;

    public CustomFormAuthenticationFilter(MessageSourceAccessor messageSourceAccessor, UserServiceImpl userService) {
        super(messageSourceAccessor);
        this.userService = userService;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        Object principal = subject.getPrincipal();
        if (principal != null) {
            SysUser sysUser = (SysUser) principal;
            sysUser.setLoginCount(sysUser.getLoginCount() + 1);
            userService.updateSysUser(sysUser);
        }
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
    }
}
