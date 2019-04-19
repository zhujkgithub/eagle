package com.soaring.eagle.admin.config.shiro.service;

import com.soaring.eagle.common.constant.ResultModel;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/22
 * Time: 13:45
 */
public interface IPermissionService {

    /**
     * 根据用户Id
     * @param userId 用户Id
     * @return 获取权限集合
     */
    ResultModel<List<String>> getPermissionsByUserId(String userId);
}
