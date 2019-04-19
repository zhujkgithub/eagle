package com.soaring.eagle.api.module.gateway.controller;

import com.soaring.eagle.common.constant.ResultModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-31
 * Time: 14:50
 * Description:
 */
@RestController
public class GatewayController {

    @RequestMapping("/api/user/demo")
    public ResultModel userDemo() {
        return ResultModel.successResultModel();
    }

    @RequestMapping("/api/user/demo/1")
    public ResultModel userDemo1() {
        return ResultModel.successResultModel();
    }

    @RequestMapping("/api/admin/demo")
    public ResultModel adminDemo() {
        return ResultModel.successResultModel();
    }
}
