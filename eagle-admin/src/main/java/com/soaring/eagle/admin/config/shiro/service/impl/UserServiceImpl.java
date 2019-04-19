package com.soaring.eagle.admin.config.shiro.service.impl;

import com.soaring.eagle.admin.feign.ISysUserService;
import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.shiro.entity.IUser;
import com.soaring.eagle.common.shiro.service.IUserService;
import com.soaring.eagle.entity.sys.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/22
 * Time: 12:47
 */

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private ISysUserService sysUserService;

    /**
     * 根据用户名字获取用户
     *
     * @param username
     * @return
     */
    @Override
    public IUser findUserByUsername(String username) {
        return sysUserService.getUserByUsername(username);
    }

    public ResultModel updateSysUser(SysUser sysUser) {
        return sysUserService.saveOrUpdate(sysUser);
    }
}
