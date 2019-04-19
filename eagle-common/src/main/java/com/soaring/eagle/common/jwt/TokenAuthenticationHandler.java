package com.soaring.eagle.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-28
 * Time: 21:25
 * Description:
 */
public class TokenAuthenticationHandler implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_SUBJECT = "subject";
    /**
     * 签名发行人
     */
    private static final String ISS = "eagle";

    /**
     * 加密
     */
    private static final String DEFAULT_SECRET = "eagle@secret";
    private String secret = DEFAULT_SECRET;
    /**
     * 设置过期时间 24小时
     */
    private static final Long DEFAULT_EXPIRATION = 864000L;
    private static Long EXPIRATION = DEFAULT_EXPIRATION;

    TokenAuthenticationHandler() {

    }

    private Claims getClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 访问需要"登录"，验证token
     *
     * @param token
     * @return
     */
    String getSubjectFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get(CLAIM_KEY_SUBJECT).toString();
    }

    /**
     * 是否过期
     *
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        return this.getClaimsFromToken(token).getExpiration().before(new Date());
    }

    /**
     * 设置token过期时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRATION * 1000L);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setIssuer(ISS)
                .setIssuedAt(new Date())
                .compact();
    }

    /**
     * 登录成功，创建token
     *
     * @param subject
     * @return
     */
    String generateToken(String subject) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_SUBJECT, subject);
        return generateToken(claims);
    }


}
