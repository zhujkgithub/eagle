package com.soaring.eagle.admin.config.freemarker;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.soaring.eagle.common.shiro.freemarker.FreeMarkerVariablesProperties;
import freemarker.template.TemplateModelException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-25
 * Time: 21:35
 * Description:
 */
@Configuration
@EnableConfigurationProperties(FreeMarkerVariablesProperties.class)
public class FreemarkerConfig {

    @Resource
    private FreeMarkerVariablesProperties variablesProperties;

    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        freemarker.template.Configuration cfg = freeMarkerConfigurer.getConfiguration();
        Map<String, Object> variables = new HashMap<>();
        variables.putAll(variablesProperties.getDirectiveBeanMap());
        variables.putAll(variablesProperties.getCons());
        cfg.setSharedVaribles(variables);
        //shiro标签
        cfg.setSharedVariable("shiro", new ShiroTags());
    }

}
