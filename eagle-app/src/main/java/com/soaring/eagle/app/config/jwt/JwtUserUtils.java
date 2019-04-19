package com.soaring.eagle.app.config.jwt;

import com.soaring.eagle.common.util.FastJsonUtils;
import com.soaring.eagle.entity.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-28
 * Time: 21:35
 * Description:
 */
public class JwtUserUtils {

    private static final String ANONYMOUS_USER = "anonymousUser";

    public static User getCurrentUser(){
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || StringUtils.isBlank(obj.toString()) || ANONYMOUS_USER.equals(obj)) {
            return null;
        }
        return FastJsonUtils.toBean(obj.toString(), User.class);
    }
}
