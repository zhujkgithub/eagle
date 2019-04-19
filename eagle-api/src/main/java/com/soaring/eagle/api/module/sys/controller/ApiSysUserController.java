package com.soaring.eagle.api.module.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soaring.eagle.api.module.sys.service.ISysUserService;
import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.constant.ResultStatus;
import com.soaring.eagle.common.controller.BaseController;
import com.soaring.eagle.entity.sys.SysUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/22
 * Time: 12:55
 */

@RestController
@RequestMapping("/api/sys/user")
public class ApiSysUserController extends BaseController {

    @Resource
    private ISysUserService sysUserService;

    /**
     * 根据用户名字获取用户
     *
     * @param username 用户名字
     * @return 根据用户名字获取用户
     */
    @RequestMapping("/get-user-by-username")
    public SysUser getUserByUsername(@RequestParam("username") String username) {
        EntityWrapper<SysUser> ew = new EntityWrapper<>();
        ew.eq("username", username);
        return sysUserService.selectOne(ew);
    }

    /**
     * 根据用户Id
     *
     * @param userId 用户Id
     * @return 获取权限集合
     */
    @RequestMapping("/get-permission-by-user-id")
    public ResultModel<List<String>> getPermissionsByUserId(@RequestParam("userId") String userId) {
        return new ResultModel<>(ResultStatus.SUCCESS, sysUserService.getPermissionsByUserId(userId));
    }

    /**
     * 新增/修改
     * @param sysUser
     * @return
     */
    @RequestMapping("/save-or-update")
    public ResultModel saveOrUpdate(@RequestBody SysUser sysUser) {
        boolean b = sysUserService.saveOrUpdate(sysUser);
        return b ? ResultModel.successResultModel() : ResultModel.failResultModel();
    }

}
