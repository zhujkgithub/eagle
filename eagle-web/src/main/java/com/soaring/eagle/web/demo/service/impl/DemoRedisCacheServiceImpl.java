package com.soaring.eagle.web.demo.service.impl;

import com.soaring.eagle.entity.sys.SysFile;
import com.soaring.eagle.web.demo.service.IDemoRedisCacheService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-04-06
 * Time: 12:57
 * Description: 假装操作了数据库，验证Redis缓存
 */
@Service
public class DemoRedisCacheServiceImpl implements IDemoRedisCacheService {

    @Override
    public SysFile saveSysFile(SysFile sysFile) {
        // 返回用户信息，带id
        return sysFile;
    }

    /**
     * @CacheEvict 应用到删除数据的方法上，调用方法时会从缓存中删除对应key的数据
     *      condition 与unless相反，只有表达式为真才会执行。
     * @param id 主键id
     * @return
     */
    @CacheEvict(value = "sysFile", key = "#root.args[0]", condition = "#result eq true")
    @Override
    public Boolean removeSysFile(Long id) {
        // 假装删除了
        return true;
    }

    /**
     *  @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
     *            key 缓存在redis中的key
     *            unless 表示条件表达式成立的话不放入缓存
     * @param id 主键id
     * @return
     */
    @Cacheable(value = "sysFile", key = "#root.args[0]", unless = "#result eq null ")
    @Override
    public SysFile getById(Long id) {
        // 假装查询到了
        System.out.println("开始查询：" + id);
        SysFile sysFile = new SysFile();
        sysFile.setId(1L);
        return sysFile;
    }

    /**
     *  @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     * @param sysFile
     * @return
     */
    @CachePut(value = "sysFile", key = "#root.args[0]", unless = "#sysFile eq null ")
    @Override
    public SysFile updateSysFile(SysFile sysFile) {
        // 假装更新了
        return sysFile;
    }
}
