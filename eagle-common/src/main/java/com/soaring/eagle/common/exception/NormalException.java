package com.soaring.eagle.common.exception;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-26
 * Time: 23:39
 * Description:
 */
public class NormalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public NormalException() {
        super();
    }

    public NormalException(String message) {
        super(message);
    }

    public NormalException(String message, Throwable cause) {
        super(message, cause);
    }

    public NormalException(Throwable cause) {
        super(cause);
    }

    protected NormalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}