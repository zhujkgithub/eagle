package com.soaring.eagle.web.controller;

import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.constant.ResultStatus;
import com.soaring.eagle.common.shiro.entity.IUser;
import com.soaring.eagle.web.config.shiro.util.ShiroUserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-04-05
 * Time: 16:48
 * Description:
 */
@Controller
public class LoginController {

    @RequestMapping("/")
    public String home() {
        // 也可用 JwtUserUtils.getCurrentUser() 判断
        IUser currentUser = ShiroUserUtils.getCurrentUser();
        if (null == currentUser || StringUtils.isBlank(currentUser.getUsername())) {
            return "redirect:/login";
        }
        return "redirect:/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    @ResponseBody
    public ResultModel index() {
        IUser currentUser = ShiroUserUtils.getCurrentUser();
        return new ResultModel<>(ResultStatus.SUCCESS, "进入系统首页");
    }

    @RequestMapping("/kickout")
    public String kickout() {
        return "kickout";
    }

}
