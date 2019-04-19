package com.soaring.eagle.admin.module.sys.feign;

import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.entity.vo.SysMenuNodeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-04-02
 * Time: 19:55
 * Description:
 */
@FeignClient("eagle-api")
public interface ISysMenuService {

    /**
     * 根据用户Id获取菜单
     *
     * @param userId
     * @return
     */
    @RequestMapping("/api/sys/menu/query-menu-by-user-id")
    ResultModel<List<SysMenuNodeVO>> queryMenuByUserId(@RequestParam("userId") Long userId);
}
