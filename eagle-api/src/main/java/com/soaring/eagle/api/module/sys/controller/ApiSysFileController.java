package com.soaring.eagle.api.module.sys.controller;

import com.soaring.eagle.api.module.sys.service.ISysFileService;
import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.constant.ResultStatus;
import com.soaring.eagle.common.controller.BaseController;
import com.soaring.eagle.entity.sys.SysFile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/29
 * Time: 17:17
 */
@RestController
@RequestMapping("/api/sys/file")
public class ApiSysFileController extends BaseController {

    @Resource
    private ISysFileService sysFileService;

    /**
     * 上传文件
     *
     * @param sysFile
     * @return
     */
    @RequestMapping("/upload")
    public ResultModel upload(@RequestBody SysFile sysFile) {
        return this.basicResultModel(sysFileService.upload(sysFile));
    }

    /**
     * 根据Id获取内容
     *
     * @param id
     * @return
     */
    @RequestMapping("/get-by-id")
    public ResultModel<SysFile> getById(@RequestParam("id") String id) {
        SysFile sysFile = sysFileService.selectById(id);
        return new ResultModel<>(ResultStatus.SUCCESS, sysFile);
    }


}
