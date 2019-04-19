package com.soaring.eagle.admin.config.spring;

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
 * Date: 2019-03-26
 * Time: 23:19
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

    /**
     * 文件上传
     * SpringBoot上传文件异常-Required request part 'file' is not present
     * 异常原因：指定了上传文件的处理器
     * 请看简书：https://www.jianshu.com/p/1eb22338da8b
     * 解决办法：注释掉，不指定文件处理器
     *
     * @return
     */
    /*@Bean("multipartResolver")
    public CommonsMultipartResolver resolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        //100M*1024*1024=104857600
        commonsMultipartResolver.setMaxUploadSize(104857600L);
        return commonsMultipartResolver;
    }*/

}
