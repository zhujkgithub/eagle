package com.soaring.eagle.web.config.spring;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.soaring.eagle.common.exception.GlobalDefaultExceptionHandler;
import com.soaring.eagle.common.fastjson.GeneralFastJsonHttpMessageConverter;
import feign.Request;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
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
 * Date: 2019-04-05
 * Time: 11:31
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
     * 注册 servlet 组件
     *
     * @return 验证码
     */
    @Bean
    public ServletRegistrationBean kaptchaServlet() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(), "/kaptcha.jpg");
        servlet.addInitParameter("kaptcha.border", "yes");
        servlet.addInitParameter("kaptcha.border.color", "105,179,90");
        servlet.addInitParameter("kaptcha.textproducer.font.color", "blue");
        servlet.addInitParameter("kaptcha.image.width", "250");
        servlet.addInitParameter("kaptcha.textproducer.font.size", "70");
        servlet.addInitParameter("kaptcha.image.height", "90");
        servlet.addInitParameter("kaptcha.textproducer.char.length", "4");
        servlet.addInitParameter("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.FishEyeGimpy");
        servlet.addInitParameter("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        return servlet;
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
     *
     * @return
     */
    @Bean
    public GlobalDefaultExceptionHandler exceptionHandler() {
        return new GlobalDefaultExceptionHandler();
    }

}
