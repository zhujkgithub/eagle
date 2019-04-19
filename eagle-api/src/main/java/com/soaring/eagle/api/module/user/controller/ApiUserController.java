package com.soaring.eagle.api.module.user.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.soaring.eagle.api.module.user.service.IUserService;
import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.constant.ResultStatus;
import com.soaring.eagle.common.controller.BaseController;
import com.soaring.eagle.common.controller.PageParam;
import com.soaring.eagle.entity.user.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-03-21
 */
@RestController
@RequestMapping("/api/user")
public class ApiUserController extends BaseController {

    @Resource
    private IUserService userService;

    /**
     * 根据用户名获取user
     *
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping("/get-by-username")
    public User getByUsername(@RequestParam("username") String username) {
        EntityWrapper<User> ewUser = new EntityWrapper<>();
        ewUser.eq("username", username);
        return userService.selectOne(ewUser);
    }

    /**
     * 保存或者更新
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/save-or-update")
    public ResultModel saveOrUpdate(@RequestBody User user) {
        return this.basicResultModel(userService.saveOrUpdate(user));
    }


    @ResponseBody
    @RequestMapping("list")
    public ResultModel list(@RequestBody PageParam pageParam) {
        Page<User> page = this.getPage(pageParam);
        Page<User> userPage = userService.queryPage(page, page.getCondition());
        return new ResultModel<>(ResultStatus.SUCCESS, userPage);
    }

}
