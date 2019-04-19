package com.soaring.eagle.api.module.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.soaring.eagle.entity.sys.SysUser;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-03-21
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据用户Id
     *
     * @param userId 用户Id
     * @return 获取权限集合
     */
    List<String> getPermissionsByUserId(String userId);

    /**
     * 新增/修改
     * @param sysUser
     * @return
     */
    boolean saveOrUpdate(SysUser sysUser);
}
