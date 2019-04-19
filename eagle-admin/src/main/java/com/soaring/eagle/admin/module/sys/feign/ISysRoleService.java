package com.soaring.eagle.admin.module.sys.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/25
 * Time: 12:51
 */
@FeignClient("eagle-api")
public interface ISysRoleService {

}
