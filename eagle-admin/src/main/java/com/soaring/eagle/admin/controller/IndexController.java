package com.soaring.eagle.admin.controller;

import com.soaring.eagle.admin.config.shiro.util.ShiroUserUtils;
import com.soaring.eagle.admin.module.sys.feign.ISysMenuService;
import com.soaring.eagle.common.constant.ImageType;
import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.util.CookieUtils;
import com.soaring.eagle.common.util.FileUploadUtil;
import com.soaring.eagle.entity.sys.SysUser;
import com.soaring.eagle.entity.vo.SysMenuNodeVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-22
 * Time: 22:15
 * Description:
 */
@Controller
public class IndexController {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Value("${file.view.path}")
    private String fileViewPath;

    @Resource
    private ISysMenuService sysMenuService;

    /**
     * 技术部首页
     *
     * @param model
     * @return
     */
    @RequiresPermissions("user:list")
    @RequestMapping("/index")
    public String index(Model model) {
        SysUser sysUser = (SysUser) ShiroUserUtils.getCurrentUser();
        List<SysMenuNodeVO> menus = sysMenuService.queryMenuByUserId(sysUser.getId()).getData();
        // 一级菜单
        List<SysMenuNodeVO> firstNode = menus.stream().filter(menu -> SysMenuNodeVO.ROOT_ID.equals(menu.getPid().toString())).collect(Collectors.toList());
        // 二级菜单
        List<SysMenuNodeVO> otherNode = menus.stream().filter(menu -> !SysMenuNodeVO.ROOT_ID.equals(menu.getPid().toString())).collect(Collectors.toList());
        firstNode.forEach(item -> {
            List<SysMenuNodeVO> nodes = item.getChildren();
            for (SysMenuNodeVO node : otherNode) {
                if (node.getPid().equals(item.getId())) {
                    nodes.add(node);
                }
            }
        });
        model.addAttribute("menus", firstNode);
        model.addAttribute("sysUser", sysUser);
        return "index";
    }

    @RequestMapping("/demo/sys/file/toUpload")
    public String toUpload(HttpServletRequest request) {
        String rememberMe = CookieUtils.getCookie(request, "rememberMe");
        String sid = CookieUtils.getCookie(request, "sid");
        return "/upload";
    }

    @PostMapping("/demo/sys/file/upload")
    @ResponseBody
    public ResultModel uploadTest(@RequestParam("file") MultipartFile file) {

        String rmFileId = FileUploadUtil.uploadMultiFile(file, fileUploadPath, ImageType.OTHER.getType(), null);

        return ResultModel.successResultModel();
    }

    @RequestMapping("/demo/sys/file/viewUpload")
    public String viewUpload(Model model) {
        model.addAttribute("fileViewPath", fileViewPath);
        return "/viewUpload";
    }

    @RequestMapping("/demo/echarts/index")
    public String echartsIndex() {
        return "/admin/echarts/index";
    }

    /**
     * 需要iframe打开
     *
     * $selectIconBtn.on("click", function () {
     *             parent.layer.open({
     *                 type: 2,
     *                 skin: "xyjtech-custom-layer",
     *                 title: "选择图标",
     *                 area: ["800px", "600px"],
     *                 fix: false, //不固定
     *                 shadeClose: false,
     *                 shade: 0.5,
     *                 scrollbar: false,
     *                 content: ["/demo/icon/index", 'yes']
     *             });
     *         });
     * @return
     */
    @RequestMapping("/demo/icon/index")
    public String iconIndex() {
        return "/common/icon_select";
    }

}
