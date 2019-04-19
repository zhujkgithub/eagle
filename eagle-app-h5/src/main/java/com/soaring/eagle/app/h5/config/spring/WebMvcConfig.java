package com.soaring.eagle.app.h5.config.spring;

import com.soaring.eagle.common.exception.GlobalDefaultExceptionHandler;
import com.soaring.eagle.common.fastjson.GeneralFastJsonHttpMessageConverter;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
     * 数据转换器
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        GeneralFastJsonHttpMessageConverter fastJsonHttpMessageConverter = new GeneralFastJsonHttpMessageConverter();
        converters.add(fastJsonHttpMessageConverter);
    }

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