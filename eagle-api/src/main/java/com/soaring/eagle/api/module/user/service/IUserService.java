package com.soaring.eagle.api.module.user.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.entity.user.User;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-03-21
 */
public interface IUserService extends IService<User> {

    /**
     * 保存或者更新用户
     *
     * @param user
     * @return
     */
    Boolean saveOrUpdate(User user);

    Page<User> queryPage(Page<User> page, Map<String, Object> condition);
}
