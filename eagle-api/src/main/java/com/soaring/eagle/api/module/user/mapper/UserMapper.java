package com.soaring.eagle.api.module.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.soaring.eagle.entity.user.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-03-21
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> queryPage(Page<User> page, Map<String, Object> condition);
}