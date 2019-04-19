package com.soaring.eagle.file.feign;

import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.entity.sys.SysFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/29
 * Time: 16:31
 */
@FeignClient("eagle-api")
public interface ISysFileService {

    /**
     * 上传文件
     *
     * @param sysFile
     * @return
     */
    @RequestMapping("/api/sys/file/upload")
    ResultModel<String> upload(@RequestBody SysFile sysFile);

    /**
     * 查看文件
     *
     * @param id
     * @return
     */
    @RequestMapping("/api/sys/file/get-by-id")
    ResultModel<SysFile> getById(@RequestParam("id") String id);
}
