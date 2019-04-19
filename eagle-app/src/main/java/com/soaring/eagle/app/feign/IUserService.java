package com.soaring.eagle.app.feign;

import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.entity.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-28
 * Time: 22:01
 * Description:
 */

@FeignClient("eagle-api")
public interface IUserService {

    /**
     * 根据用户名获取user
     *
     * @param username 用户名
     * @return
     */
    @RequestMapping("/api/user/get-by-username")
    User getByUsername(@RequestParam("username") String username);

    /**
     * 保存或者更新
     *
     * @param user
     * @return
     */
    @RequestMapping("/api/user/save-or-update")
    ResultModel<String> saveOrUpdate(@RequestBody User user);
}
