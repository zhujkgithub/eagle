package com.soaring.eagle.api.module.user.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soaring.eagle.api.module.user.mapper.UserMapper;
import com.soaring.eagle.api.module.user.service.IUserService;
import com.soaring.eagle.common.util.EagleStringUtils;
import com.soaring.eagle.common.util.SnowFlakeWorkerUtil;
import com.soaring.eagle.entity.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-03-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 保存或者更新用户
     *
     * @param user
     * @return
     */
    @Override
    public Boolean saveOrUpdate(User user) {
        boolean flag;
        if(EagleStringUtils.isBlank(user.getId())) {
            user.setId(SnowFlakeWorkerUtil.getInstance().nextId());
            flag = this.insert(user);
        } else {
            flag = this.updateById(user);
        }
        return flag;
    }

    @Override
    public Page<User> queryPage(Page<User> page, Map<String, Object> condition) {
        List<User> users = userMapper.queryPage(page, condition);
        page.setRecords(users);
        return page;
    }
}
