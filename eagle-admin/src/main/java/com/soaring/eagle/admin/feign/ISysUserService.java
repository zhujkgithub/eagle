package com.soaring.eagle.admin.feign;

import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.shiro.entity.IUser;
import com.soaring.eagle.entity.sys.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/22
 * Time: 12:50
 */
@FeignClient("eagle-api")
public interface ISysUserService {

    /**
     * 根据用户名字获取用户
     *
     * @param username 用户名字
     * @return 根据用户名字获取用户
     */
    @RequestMapping("/api/sys/user/get-user-by-username")
    SysUser getUserByUsername(@RequestParam("username") String username);

    /**
     * 添加/更新
     * @param sysUser
     * @return
     */
    @RequestMapping("/api/sys/user/save-or-update")
    ResultModel saveOrUpdate(@RequestBody SysUser sysUser);
}
