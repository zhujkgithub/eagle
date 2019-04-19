package com.soaring.eagle.file.controller;

import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.constant.ResultStatus;
import com.soaring.eagle.common.util.SnowFlakeWorkerUtil;
import com.soaring.eagle.entity.sys.SysFile;
import com.soaring.eagle.file.feign.ISysFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/29
 * Time: 16:30
 */
@RestController
@RequestMapping("/sys/file")
public class SysFileController {

    @Resource
    private ISysFileService sysFileService;

    /**
     * 文件上传路径
     */
    @Value("${file.upload.path}")
    private String fileUploadPath;

    /**
     * 文件路径
     */
    @Value("${file.view.path}")
    private String fileViewPath;

    /**
     * 上传文件类型限制
     */
    @Value("${file.upload.type}")
    private String fileUploadType;

    /**
     * 验证是否从远程git仓库读取到了配置文件
     * 个人理解：
     * springBoot 部署在生产环境上，一般是以jar包的方式，
     * 改动配置文件需要重新替换jar包
     * 因此把配置文件写在远程git仓库，只需要更改git上的配置文件即可
     *
     * @return
     */
    @RequestMapping("/check/git")
    public ResultModel checkGit() {
        System.out.println(fileUploadPath);
        System.out.println(fileViewPath);
        System.out.println(fileUploadType);
        return ResultModel.successResultModel();
    }

    /**
     * 检查上传的文件类型
     *
     * @param file
     * @return
     */
    private boolean checkFileType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase().trim();
        return fileUploadType.contains(extName);
    }

    /**
     * 单个文件上传
     *
     * @param file
     * @param type
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload")
    public ResultModel upload(@RequestParam(value = "file") MultipartFile file,
                              @RequestParam(value = "type", defaultValue = "other") String type) throws IOException {
        if (file == null) {
            return new ResultModel<>(ResultStatus.SUCCESS, "请选择要上传的文件");
        }
        if (!checkFileType(file)) {
            return new ResultModel<>(ResultStatus.FAIL, "请上传正确的文件类型");
        }
        // 开始上传
        String originalName = file.getOriginalFilename();
        assert originalName != null;
        // 得到文件后缀名字
        String ext = originalName.substring(originalName.lastIndexOf(".") + 1);
        // 文件路径
        String fileName = System.currentTimeMillis() + "." + ext;
        String path = File.separator + type + File.separator + fileName;
        File targetFile = new File(path);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        // 保存文件
        file.transferTo(targetFile);
        // 保存sys_file
        SysFile sysFile = new SysFile();
        long id = SnowFlakeWorkerUtil.getInstance().nextId();
        sysFile.setId(id);
        sysFile.setName(originalName);
        sysFile.setFilename(fileName);
        sysFile.setPath(path);
        sysFile.setSize(file.getSize());
        sysFile.setType(type);
        sysFile.setCreateTime(new Date());
        sysFileService.upload(sysFile);
        return new ResultModel<>(ResultStatus.SUCCESS, id);
    }

    /**
     * 查看文件
     *
     * @param id
     * @param response
     * @return 在windows目录下的图片无法显示，在文件服务器没问题
     * <p>
     * description: Spring Boot项目@RestController使用重定向redirect
     * Spring MVC项目中页面重定向一般使用return "redirect:/other/controller/";即可。
     * 而Spring Boot使用了@RestController注解，上述写法只能返回字符串，解决方法如下：
     * 将一个HttpServletResponse参数添加到处理程序方法然后调用response.sendRedirect("some-url");
     */
    @RequestMapping("open")
    public void upload(@RequestParam("id") String id, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        SysFile sysFile = sysFileService.getById(id).getData();
        if (sysFile == null) {
            return;
        }
        String path = fileViewPath + sysFile.getPath();
        try {
            response.sendRedirect(path);
//            response.sendRedirect("https://www.baidu.com/img/bd_logo1.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
