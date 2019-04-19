package com.soaring.eagle.common.exception;

import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.constant.ResultStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-26
 * Time: 23:38
 * Description: 全局异常处理（也可以返回页面）
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultModel<String> excption(Exception e) {
        if (e instanceof NormalException) {
            return new ResultModel<>(ResultStatus.FAIL, e.getMessage());
        }
        return new ResultModel<>(ResultStatus.FAIL, "这个是非普通业务异常的异常-->" + e.getMessage());
    }
}
