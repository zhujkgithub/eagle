package com.soaring.eagle.api.module.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soaring.eagle.entity.sys.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 系统用户表 Mapper 接口
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-03-21
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户Id
     *
     * @param userId 用户Id
     * @return 获取权限集合
     */
    List<String> getPermissionsByUserId(@Param("userId") String userId);
}