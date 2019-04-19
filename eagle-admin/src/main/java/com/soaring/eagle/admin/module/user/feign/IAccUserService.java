package com.soaring.eagle.admin.module.user.feign;

import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.controller.PageParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/4/2
 * Time: 17:16
 */
@FeignClient("eagle-api")
public interface IAccUserService {

    @RequestMapping("/api/user/list")
    ResultModel queryPage(PageParam pageParam);

}
