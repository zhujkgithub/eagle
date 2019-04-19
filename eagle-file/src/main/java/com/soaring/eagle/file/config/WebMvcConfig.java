package com.soaring.eagle.file.config;

import com.soaring.eagle.common.exception.GlobalDefaultExceptionHandler;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-28
 * Time: 21:34
 * Description:
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * feign请求超时时间，读取数据超时时间
     *
     * @return
     */
    @Bean
    Request.Options feignOptions() {
        return new Request.Options(300 * 1000, 300 * 1000);
    }

    /**
     * 全局异常处理
     * @return
     */
    @Bean
    public GlobalDefaultExceptionHandler handlerExceptionResolver(){
        return new GlobalDefaultExceptionHandler();
    }
}