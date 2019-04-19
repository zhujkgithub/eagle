package com.soaring.eagle.api.module.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soaring.eagle.api.module.sys.mapper.SysUserMapper;
import com.soaring.eagle.api.module.sys.service.ISysUserService;
import com.soaring.eagle.common.util.SnowFlakeWorkerUtil;
import com.soaring.eagle.entity.sys.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-03-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 根据用户Id
     *
     * @param userId 用户Id
     * @return 获取权限集合
     */
    @Override
    public List<String> getPermissionsByUserId(String userId) {
        return sysUserMapper.getPermissionsByUserId(userId);
    }

    /**
     * 新增/修改
     *
     * @param sysUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(SysUser sysUser) {
        boolean b;
        if (StringUtils.isBlank(sysUser.getId().toString())){
            SnowFlakeWorkerUtil instance = SnowFlakeWorkerUtil.getInstance();
            sysUser.setId(instance.nextId());
            b = this.insert(sysUser);
        } else {
            b = this.updateById(sysUser);
        }
        return b;
    }
}
