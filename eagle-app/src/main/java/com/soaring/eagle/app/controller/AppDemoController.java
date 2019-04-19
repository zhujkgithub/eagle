package com.soaring.eagle.app.controller;

import com.soaring.eagle.app.config.jwt.JwtUserUtils;
import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.controller.BaseController;
import com.soaring.eagle.common.exception.NormalException;
import com.soaring.eagle.entity.sys.SysFile;
import com.soaring.eagle.entity.user.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/4/2
 * Time: 9:15
 */
@RestController
public class AppDemoController extends BaseController {

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
    @RequestMapping("/app/demo")
    public ResultModel appDemo() {
        User currentUser = JwtUserUtils.getCurrentUser();
        return ResultModel.successResultModel();
    }

    /**
     * 无需登录
     *
     * @return
     */
    @RequestMapping("/demo")
    public ResultModel demo(@RequestBody @Valid SysFile sysFile, BindingResult result) {
        if (result.hasErrors()) {
            throw new NormalException(getAllErrors(result));
        }
        return ResultModel.successResultModel();
    }

}
