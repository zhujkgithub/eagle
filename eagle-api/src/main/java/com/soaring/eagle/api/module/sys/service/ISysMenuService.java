package com.soaring.eagle.api.module.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.soaring.eagle.entity.sys.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-04-02
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据用户Id获取菜单
     *
     * @param userId
     * @return
     */
    List<SysMenu> queryMenuByUserId(String userId);
}
