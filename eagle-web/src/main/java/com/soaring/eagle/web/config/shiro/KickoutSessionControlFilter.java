package com.soaring.eagle.web.config.shiro;

import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.constant.ResultStatus;
import com.soaring.eagle.common.util.FastJsonUtils;
import com.soaring.eagle.entity.user.User;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-04-06
 * Time: 17:57
 * Description:
 */
public class KickoutSessionControlFilter extends AccessControlFilter {

    /**
     * 踢出后到的地址
     */
    private String kickoutUrl;
    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private boolean kickoutAfter = false;
    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 1;

    private SessionManager sessionManager;

    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * 设置Cache的key的前缀
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro_redis_cache");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }

        Session session = subject.getSession();
        User user = (User) subject.getPrincipal();
        String username = user.getUsername();
        Serializable sessionId = session.getId();

        //读取缓存   没有就存入
        Deque<Serializable> deque = cache.get(username);
        // 可能会空指针异常
        if (null == deque) {
            deque = new ArrayDeque<>();
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            //将sessionId存入队列
            deque.push(sessionId);
            //将用户的sessionId队列缓存
            cache.put(username, deque);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if (kickoutAfter) {
                //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else {
                //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            //踢出后再更新下缓存队列
            cache.put(username, deque);

            try {
                //获取被踢出的sessionId的session对象
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {
                //ignore exception
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            try {
                //退出登录
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(request);

            //判断是不是Ajax请求
            if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
                PrintWriter out = null;
                try {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    out = httpServletResponse.getWriter();
                    out.println(FastJsonUtils.toJSONString(new ResultModel<>(ResultStatus.FAIL, "您已经在其他地方登录，请重新登录！")));
                    out.flush();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
            } else {
                //重定向
                WebUtils.issueRedirect(request, response, kickoutUrl);
            }
            return false;
        }

        return true;
    }
}
