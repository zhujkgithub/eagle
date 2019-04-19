package com.soaring.eagle.admin.feign;

import com.soaring.eagle.common.constant.ResultModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/22
 * Time: 13:47
 */
@FeignClient("eagle-api")
public interface ISysPermissionService {

    /**
     * 根据用户Id
     *
     * @param userId 用户Id
     * @return 获取权限集合
     */
    @RequestMapping("/api/sys/user/get-permission-by-user-id")
    ResultModel<List<String>> getPermissionsByUserId(@RequestParam("userId") String userId);
}
