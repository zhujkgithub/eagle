package com.soaring.eagle.web.demo.service;

import com.soaring.eagle.entity.sys.SysFile;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-04-06
 * Time: 12:57
 * Description:
 */
public interface IDemoRedisCacheService {

    SysFile saveSysFile(SysFile sysFile);

    Boolean removeSysFile(Long id);

    SysFile getById(Long id);

    SysFile updateSysFile(SysFile sysFile);
}
