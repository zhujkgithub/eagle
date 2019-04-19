package com.soaring.eagle.api.module.sys.controller;


import com.soaring.eagle.api.module.sys.service.ISysMenuService;
import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.constant.ResultStatus;
import com.soaring.eagle.common.controller.BaseController;
import com.soaring.eagle.entity.sys.SysMenu;
import com.soaring.eagle.entity.vo.SysMenuNodeVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author hautxxxyzjk@163.com
 * @since 2019-04-02
 */
@RestController
@RequestMapping("/api/sys/menu")
public class ApiSysMenuController extends BaseController {

    @Resource
    private ISysMenuService sysMenuService;

    private List<SysMenuNodeVO> getSysMenuNodeVO(List<SysMenu> menus) {
        List<SysMenuNodeVO> sysMenuNodeVOS = new ArrayList<>();
        menus.forEach(menu -> {
            SysMenuNodeVO sysMenuNodeVO = new SysMenuNodeVO();
            sysMenuNodeVO.setId(menu.getId());
            sysMenuNodeVO.setPid(menu.getPid());
            sysMenuNodeVO.setPids(menu.getPids());
            sysMenuNodeVO.setName(menu.getName());
            sysMenuNodeVO.setSort(menu.getSort());
            sysMenuNodeVO.setHref(menu.getHref());
            sysMenuNodeVO.setIcon(menu.getIcon());
            sysMenuNodeVOS.add(sysMenuNodeVO);
        });
        return sysMenuNodeVOS;
    }

    /**
     * 根据用户Id获取菜单
     *
     * @param userId
     * @return
     */
    @RequestMapping("/query-menu-by-user-id")
    public ResultModel<List<SysMenuNodeVO>> queryMenuByUserId(@RequestParam("userId") String userId) {
        List<SysMenu> menus = sysMenuService.queryMenuByUserId(userId);
        List<SysMenuNodeVO> sysMenuNodeVOs = getSysMenuNodeVO(menus);
        return new ResultModel<>(ResultStatus.SUCCESS, sysMenuNodeVOs);
    }

}
