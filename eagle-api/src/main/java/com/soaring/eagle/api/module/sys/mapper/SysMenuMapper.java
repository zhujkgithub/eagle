package com.soaring.eagle.api.module.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soaring.eagle.entity.sys.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-04-02
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户Id获取菜单
     *
     * @param userId
     * @return
     */
    List<SysMenu> queryMenuByUserId(@Param("userId") String userId);
}