package com.soaring.eagle.admin.config.shiro.service.impl;

import com.soaring.eagle.admin.config.shiro.service.IPermissionService;
import com.soaring.eagle.admin.feign.ISysPermissionService;
import com.soaring.eagle.common.constant.ResultModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/22
 * Time: 13:47
 */
@Service("permissionService")
public class PermissionServiceImpl implements IPermissionService {

    @Resource
    private ISysPermissionService sysPermissionService;

    /**
     * 根据用户Id
     *
     * @param userId 用户Id
     * @return 获取权限集合
     */
    @Override
    public ResultModel<List<String>> getPermissionsByUserId(String userId) {
        return sysPermissionService.getPermissionsByUserId(userId);
    }
}
