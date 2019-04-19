package com.soaring.eagle.api.module.webservice;

import javax.jws.WebMethod;
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

@WebService
public interface IDemoWebService {

    @WebMethod
    String demo();
}
