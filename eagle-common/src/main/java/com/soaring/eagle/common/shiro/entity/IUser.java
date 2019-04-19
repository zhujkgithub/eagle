package com.soaring.eagle.common.shiro.entity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/22
 * Time: 12:30
 */
public interface IUser {

    /**
     * 用户名
     */
    String getUsername();

    /**
     * 密码
     */
    String getPassword();

    /**
     * 是否展示
     */
    Integer getDisabled();

    /**
     * 是否删除
     */
    Integer getDeleted();

    /**
     * 是否锁定
     */
    Integer getLocked();
}
