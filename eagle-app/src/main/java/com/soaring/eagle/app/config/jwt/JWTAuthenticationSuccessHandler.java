package com.soaring.eagle.app.config.jwt;

import com.soaring.eagle.app.feign.IUserService;
import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.constant.ResultStatus;
import com.soaring.eagle.common.util.FastJsonUtils;
import com.soaring.eagle.common.util.IpUtils;
import com.soaring.eagle.entity.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-28
 * Time: 21:29
 * Description:
 */
@Component
public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private IUserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Object principal = authentication.getPrincipal();
        if (principal != null) {
            User user = (User) principal;
            response.setContentType("application/json");
            User updateUser = new User();
            updateUser.setId(user.getId());
            updateUser.setLastLoginIp(IpUtils.getIp(request));
            updateUser.setLastLoginTime(new Date());
            updateUser.setLoginCount(user.getLoginCount() + 1);
            userService.saveOrUpdate(updateUser);
            response.getWriter().write(FastJsonUtils.toJSONString(new ResultModel<>(ResultStatus.SUCCESS, user)));
        }
    }

}
