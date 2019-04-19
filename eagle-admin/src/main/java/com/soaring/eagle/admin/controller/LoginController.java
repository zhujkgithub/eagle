package com.soaring.eagle.admin.controller;

import com.soaring.eagle.admin.config.shiro.util.ShiroUserUtils;
import com.soaring.eagle.common.shiro.entity.IUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/21
 * Time: 11:52
 */

@Controller
public class LoginController {

    /**
     * 解决直接访问 http://localhost:9002 进入系统页面
     *
     * @return
     */
    @RequestMapping("/")
    public String home() {
        // 也可用 ShiroUserUtils.getSession() 判断
        IUser currentUser = ShiroUserUtils.getCurrentUser();
        if (null == currentUser || StringUtils.isBlank(currentUser.getUsername())) {
            return "redirect:/login";
        }
        return "redirect:/index";
    }

    @RequestMapping("/login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }
}
