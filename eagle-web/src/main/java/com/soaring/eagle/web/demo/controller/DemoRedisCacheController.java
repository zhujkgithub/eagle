package com.soaring.eagle.web.demo.controller;

import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.entity.sys.SysFile;
import com.soaring.eagle.web.demo.service.IDemoRedisCacheService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-04-06
 * Time: 12:55
 * Description:
 */
@RestController
@RequestMapping("/demo/redis/cache")
public class DemoRedisCacheController {

    @Resource
    private IDemoRedisCacheService demoRedisCacheService;

    @RequestMapping("/saveSysFile")
    public ResultModel saveSysFile() {
        SysFile sysFile = new SysFile();
        demoRedisCacheService.saveSysFile(sysFile);
        return ResultModel.successResultModel();
    }

    @RequestMapping("/removeSysFile")
    public ResultModel removeSysFile() {
        demoRedisCacheService.removeSysFile(1L);
        return ResultModel.successResultModel();
    }

    @RequestMapping("/getById")
    public ResultModel getById() {
        demoRedisCacheService.getById(2L);
        return ResultModel.successResultModel();
    }

    @RequestMapping("/updateSysFile")
    public ResultModel updateSysFile() {
        SysFile sysFile = new SysFile();
        demoRedisCacheService.updateSysFile(sysFile);
        return ResultModel.successResultModel();
    }

}
