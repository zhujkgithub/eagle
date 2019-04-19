package com.soaring.eagle.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/4/3
 * Time: 16:30
 * description: MD5加密：使用shiro
 */
public class EagleMd5Util {

    private static final String DEFAULT_ALGORITHM_NAME = "md5";
    private static final int DEFAULT_HASH_ITERATIONS = 2;
    private String algorithmName = DEFAULT_ALGORITHM_NAME;
    private int hashIterations = DEFAULT_HASH_ITERATIONS;

    private static final String PWD_REGEX = "(?!^(\\d+|[a-zA-Z]+|[_~!@#$%^&*?]+)$)^[\\w_~!@#$%\\\\^&*?]{6,20}$";

    public EagleMd5Util() {
    }

    public EagleMd5Util(String algorithmName, int hashIterations) {
        this.algorithmName = algorithmName;
        this.hashIterations = hashIterations;
    }

    public String encrypt(String passowrd) {
        return (new SimpleHash(this.algorithmName, passowrd)).toHex();
    }

    public String encrypt(String passowrd, String salt) {
        return (new SimpleHash(this.algorithmName, passowrd, salt, this.hashIterations)).toHex();
    }

    public static boolean matchPwd(String password) {
        Pattern pattern = Pattern.compile(PWD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


}
