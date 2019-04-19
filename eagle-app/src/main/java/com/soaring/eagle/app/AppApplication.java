package com.soaring.eagle.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-28
 * Time: 19:46
 * Description:
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class AppApplication {

    public static void main(String[] args){
        SpringApplication.run(AppApplication.class, args);
    }
}
