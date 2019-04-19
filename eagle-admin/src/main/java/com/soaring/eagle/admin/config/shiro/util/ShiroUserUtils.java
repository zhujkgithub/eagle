package com.soaring.eagle.admin.config.shiro.util;

import com.soaring.eagle.common.shiro.entity.IUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-04-05
 * Time: 19:45
 * Description:
 */
public class ShiroUserUtils {
    /**
     * 获取用户
     *
     * @return
     */
    public static IUser getCurrentUser() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return null;
        }
        return (IUser) principal;
    }

    /**
     * 获取Shiro Subject
     *
     * @return
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取shiro session
     *
     * @return
     */
    public static Session getSession() {
        return getSubject().getSession();
    }
}
