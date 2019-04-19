package com.soaring.eagle.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-28
 * Time: 23:41
 * Description:
 */
public class EagleBCryptPasswordEncoderUtil {

    /**
     * eagle-app 注册用户使用 bcrypt 加密
     *
     * @param password 待加密的密码
     * @return
     */
    public static String encode(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    

}
