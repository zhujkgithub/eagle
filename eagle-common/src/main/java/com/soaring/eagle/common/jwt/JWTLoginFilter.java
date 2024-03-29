package com.soaring.eagle.common.jwt;

import com.soaring.eagle.common.util.FastJsonUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-28
 * Time: 21:24
 * Description:
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    private AuthenticationSuccessHandler successHandler;

    public JWTLoginFilter() {
    }

    public JWTLoginFilter(AuthenticationManager authManager) {
        setAuthenticationManager(authManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        TokenAuthenticationHandler tokenAuthenticationHandler = new TokenAuthenticationHandler();
        Object obj = auth.getPrincipal();
        if(obj != null) {
            UserDetails userDetails = (UserDetails)obj;
            String token = tokenAuthenticationHandler.generateToken(FastJsonUtils.toJSONNoConfig(userDetails));
            res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
        }

        if(successHandler != null) {
            successHandler.onAuthenticationSuccess(req, res, auth);
        }
    }

    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }
}
