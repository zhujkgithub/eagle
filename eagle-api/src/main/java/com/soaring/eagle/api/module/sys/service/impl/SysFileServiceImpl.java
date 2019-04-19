package com.soaring.eagle.api.module.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soaring.eagle.api.module.sys.mapper.SysFileMapper;
import com.soaring.eagle.api.module.sys.service.ISysFileService;
import com.soaring.eagle.entity.sys.SysFile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-03-29
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

    @Resource
    private SysFileMapper sysFileMapper;

    /**
     * 上传文件
     *
     * @param sysFile
     * @return
     */
    @Override
    public boolean upload(SysFile sysFile) {
        return this.insert(sysFile);
    }

}
