package com.soaring.eagle.common.shiro.freemarker;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-25
 * Time: 21:31
 * Description:
 */
@Component
@ConfigurationProperties(prefix = "freemarker.variables")
public class FreeMarkerVariablesProperties implements BeanFactoryAware {
    private BeanFactory beanFactory;
    private Map<String, String> directive = new HashMap<>();
    private Map<String, String> cons = new HashMap<>();

    public FreeMarkerVariablesProperties() {
    }

    public Map<String, String> getDirective() {
        return this.directive;
    }

    public Map<String, String> getCons() {
        return this.cons;
    }

    public Map<String, Object> getDirectiveBeanMap() {
        Map<String, String> directives = this.getDirective();
        if (directives.isEmpty()) {
            return Collections.emptyMap();
        } else {
            Map<String, Object> resultMap = new HashMap<>(directives.size());
            Iterator var5 = directives.entrySet().iterator();

            while (var5.hasNext()) {
                Map.Entry entry = (Map.Entry) var5.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                resultMap.put(key, this.beanFactory.getBean(value, Object.class));
            }

            return resultMap;
        }
    }

    public void setDirective(Map<String, String> directive) {
        this.directive = directive;
    }

    public void setCons(Map<String, String> cons) {
        this.cons = cons;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}

