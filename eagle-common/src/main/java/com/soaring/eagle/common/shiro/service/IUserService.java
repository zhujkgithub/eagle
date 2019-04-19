package com.soaring.eagle.common.shiro.service;

import com.soaring.eagle.common.shiro.entity.IUser;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/22
 * Time: 12:44
 */
public interface IUserService {

    /**
     * 根据用户名字获取用户
     *
     * @param username
     * @return
     */
    IUser findUserByUsername(String username);
}
