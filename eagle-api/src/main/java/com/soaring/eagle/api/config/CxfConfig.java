package com.soaring.eagle.api.config;

import com.soaring.eagle.api.module.webservice.DemoWebServiceImpl;
import com.soaring.eagle.api.module.webservice.IDemoWebService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-04-08
 * Time: 19:54
 * Description:
 */
@Configuration
public class CxfConfig {

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public IDemoWebService demoWebService() {
        return new DemoWebServiceImpl();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), demoWebService());
        endpoint.publish("/demoWebService");
        return endpoint;
    }
}
