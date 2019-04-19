package com.soaring.eagle.api.config;

import com.soaring.eagle.common.exception.GlobalDefaultExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/28
 * Time: 13:06
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {


    /**
     * 全局异常处理
     *
     * @return
     */
    @Bean
    public GlobalDefaultExceptionHandler exceptionHandler() {
        return new GlobalDefaultExceptionHandler();
    }

    /**
     * 因为swagger 加上
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/").setCachePeriod(0);
    }
}
