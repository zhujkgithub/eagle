package com.soaring.eagle.api.module.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soaring.eagle.api.module.sys.mapper.SysMenuMapper;
import com.soaring.eagle.api.module.sys.service.ISysMenuService;
import com.soaring.eagle.entity.sys.SysMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-04-02
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * 根据用户Id获取菜单
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> queryMenuByUserId(String userId) {
        return this.baseMapper.queryMenuByUserId(userId);
    }
}
