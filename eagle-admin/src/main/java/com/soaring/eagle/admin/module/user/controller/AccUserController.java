package com.soaring.eagle.admin.module.user.controller;

import com.soaring.eagle.admin.module.user.feign.IAccUserService;
import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.controller.BaseController;
import com.soaring.eagle.common.controller.PageParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/4/2
 * Time: 17:16
 */
@Controller
@RequestMapping("/user")
public class AccUserController extends BaseController {

    @Resource
    private IAccUserService accUserService;

    /**
     * 用户管理首页
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "/admin/user/index";
    }

    @ResponseBody
    @RequestMapping("list")
    public Object queryPage(PageParam pageParam) {
        ResultModel model = accUserService.queryPage(pageParam);
        return model.getData();
    }

}
