package com.soaring.eagle.web.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-04-06
 * Time: 15:59
 * Description: Idea修改配置文件端口号，启动多个实例，测试session共享，通过
 */
@RestController
@RequestMapping("/demo/redis/session")
public class DemoRedisSessionController {

    @RequestMapping("/set")
    public String set(HttpSession session){
        session.setAttribute("sessionId",session.getId());
        session.setAttribute("sessionUserId","123");
        return "test";
    }

    @RequestMapping("/get")
    public String get(HttpSession session){
        String sessionId = session.getAttribute("sessionId").toString();
        String sessionUserId = session.getAttribute("sessionUserId").toString();
        return sessionId + "^" +  sessionUserId;
    }
}
