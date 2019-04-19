package com.soaring.eagle.common.shiro;

import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.constant.ResultStatus;
import com.soaring.eagle.common.util.FastJsonUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/28
 * Time: 17:35
 */
public class KaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MessageSourceAccessor messageSourceAccessor;

    public KaptchaFormAuthenticationFilter(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        if (request.getAttribute(getFailureKeyAttribute()) != null) {
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        if ("XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
            PrintWriter out = null;
            try {
                out = httpServletResponse.getWriter();
                out.println(FastJsonUtils.toJSONString(new ResultModel<>(ResultStatus.SUCCESS, "登录成功")));
                out.flush();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return false;
        }

        return super.onLoginSuccess(token, subject, request, response);

    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("utf-8");
        if ("XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
            String className = e.getClass().getName();
            ResultModel rm = new ResultModel<>(ResultStatus.FAIL, messageSourceAccessor.getMessage(className));
            PrintWriter out = null;
            try {
                out = httpServletResponse.getWriter();
                out.println(FastJsonUtils.toJSONString(rm));
                out.flush();
            } catch (IOException e1) {
                if (logger.isInfoEnabled()) {
                    e1.printStackTrace();
                }
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return false;
        }
        return super.onLoginFailure(token, e, request, response);
    }

    public MessageSourceAccessor getMessageSourceAccessor() {
        return messageSourceAccessor;
    }

    public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }


}
