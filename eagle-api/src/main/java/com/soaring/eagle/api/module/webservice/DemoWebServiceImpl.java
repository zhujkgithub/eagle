package com.soaring.eagle.api.module.webservice;

import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-04-08
 * Time: 19:37
 * Description:
 */
@WebService(targetNamespace="http://webservice.module.api.eagle.soaring.com/",
        endpointInterface = "com.soaring.eagle.api.module.webservice.IDemoWebService")
@Component
public class DemoWebServiceImpl implements IDemoWebService {

    @Override
    public String demo() {
        return "demo";
    }
}
