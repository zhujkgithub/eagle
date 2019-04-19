package com.soaring.eagle.api.module.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.soaring.eagle.entity.sys.SysFile;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-03-29
 */
public interface ISysFileService extends IService<SysFile> {

    /**
     * 上传文件
     *
     * @param sysFile
     * @return
     */
    boolean upload(SysFile sysFile);
}
