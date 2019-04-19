package com.soaring.eagle.app.h5.controller;

import com.soaring.eagle.app.h5.config.jwt.JwtUserUtils;
import com.soaring.eagle.app.h5.feign.IUserService;
import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.controller.BaseController;
import com.soaring.eagle.common.controller.PageParam;
import com.soaring.eagle.entity.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/4/2
 * Time: 9:15
 */
@Controller
public class AppDemoController extends BaseController {

    @Resource
    private IUserService userService;

    /**
     * 需要登录
     * 登录成功之后，会得到token
     * 例子：后面的token是登录成功之后获取的
     * 可以直接使用idea自带的工具：Tools-Http Client-Test RESTful Web Service
     * 或者postman
     * 在Headers需要加上：Authorization=Bearer eyJhbGciOiJIUzUxMiJ9.eyJjcmVhdGVkIjoxNTU0MTY5NzkxMzg4LCJzdWJqZWN0Ijoie1wiYWNjb3VudE5vbkV4cGlyZWRcIjp0cnVlLFwiYWNjb3VudE5vbkxvY2tlZFwiOnRydWUsXCJhZ2VcIjowLFwiYXV0aG9yaXRpZXNcIjpbXSxcImJpcnRoZGF5XCI6bnVsbCxcImNyZWF0ZVRpbWVcIjpudWxsLFwiY3JlYXRlVXNlclwiOjAsXCJjcmVkZW50aWFsc05vbkV4cGlyZWRcIjp0cnVlLFwiZGVsZXRlZFwiOjAsXCJkaXNhYmxlZFwiOjAsXCJlbmFibGVkXCI6dHJ1ZSxcImlkXCI6MSxcImlkQ2FyZFwiOlwiXCIsXCJsYXN0TG9naW5JcFwiOlwiMTI3LjAuMC4xXCIsXCJsYXN0TG9naW5UaW1lXCI6XCIyMDE5LTA0LTAyIDA5OjM1OjM2XCIsXCJsb2dpbkNvdW50XCI6MCxcIm1vYmlsZVwiOlwiXCIsXCJuaWNrbmFtZVwiOlwiXCIsXCJwYXNzd29yZFwiOlwiJDJhJDEwJFFkOERYeURBUG55cUw1S3dGYmRCNy5iQTVWZlJmSkZsWXJOdmVJSzZUS3Q3LmtZdi5ZVkpLXCIsXCJwaG9uZVwiOlwiXCIsXCJwaG90b1wiOlwiXCIsXCJyZWFsbmFtZVwiOlwiXCIsXCJzZXhcIjowLFwic3RhdHVzXCI6MCxcInR5cGVcIjowLFwidXBkYXRlVGltZVwiOm51bGwsXCJ1cGRhdGVVc2VyXCI6MCxcInVzZXJuYW1lXCI6XCIxODMzOTk5MDIzMFwifSIsImV4cCI6MTU1NTAzMzc5MX0.VyDdY86J3zwae3miTw5WR0AwTkOseQz5cmMCfc3J8NzxUDE_r_7GqR1x5tQwZmWDyKiGYSkpHSWk7sY9fdAgSg
     *
     * @return
     */
    @RequestMapping("/app/h5/demo")
    @ResponseBody
    public ResultModel appH5Demo() {
        User currentUser = JwtUserUtils.getCurrentUser();
        return ResultModel.successResultModel();
    }

    /**
     * 无需登录
     *
     * @return
     */
    @RequestMapping("/demo")
    public String demo() {
        return "/user/index";
    }

    @RequestMapping("/demo/queryPage")
    public String indexList(@RequestBody PageParam pageParam,
                            Model model) {
        ResultModel resultModel = userService.queryPage(pageParam);
        model.addAttribute("user", getRecords(resultModel));
        return "/user/index-list";
    }

}
