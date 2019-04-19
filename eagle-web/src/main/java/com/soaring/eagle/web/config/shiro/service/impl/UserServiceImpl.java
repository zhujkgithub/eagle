package com.soaring.eagle.web.config.shiro.service.impl;

import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.shiro.entity.IUser;
import com.soaring.eagle.common.shiro.service.IUserService;
import com.soaring.eagle.entity.sys.SysUser;
import com.soaring.eagle.entity.user.User;
import com.soaring.eagle.web.feign.IAccUserService;
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
    private IAccUserService accUserService;

    /**
     * 根据用户名字获取用户
     *
     * @param username
     * @return
     */
    @Override
    public IUser findUserByUsername(String username) {
        return accUserService.getByUsername(username);
    }

    public ResultModel updateSysUser(User user) {
        return accUserService.saveOrUpdate(user);
    }
}
