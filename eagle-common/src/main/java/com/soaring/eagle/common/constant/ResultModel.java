package com.soaring.eagle.common.constant;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/20
 * Time: 15:50
 */
public class ResultModel<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 值
     */
    private String value;

    /**
     * 信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;


    public ResultModel() {
    }

    public ResultModel(String value, String message) {
        this.value = value;
        this.message = message;
        this.data = null;
    }

    public ResultModel(String value, String message, T data) {
        this.value = value;
        this.message = message;
        this.data = data;
    }

    public ResultModel(ResultStatus resultStatus) {
        this.value = resultStatus.getValue();
        this.message = resultStatus.getMessage();
        this.data = null;
    }

    public ResultModel(ResultStatus resultStatus, T data) {
        this.value = resultStatus.getValue();
        this.message = resultStatus.getMessage();
        this.data = data;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResultModel successResultModel(){
        return new ResultModel<>(ResultStatus.SUCCESS);
    }

    public static ResultModel failResultModel(){
        return new ResultModel<>(ResultStatus.FAIL);
    }

    public static ResultModel<String> defaultSuccess(String message) {
        if (StringUtils.isNotBlank(message)) {
            return new ResultModel<>(ResultStatus.SUCCESS.getValue(), message);
        } else {
            return new ResultModel<>(ResultStatus.SUCCESS);
        }
    }

    public static ResultModel<String> defaultFail(String message) {
        if (StringUtils.isNotBlank(message)) {
            return new ResultModel<>(ResultStatus.FAIL.getValue(), message);
        } else {
            return new ResultModel<>(ResultStatus.FAIL);
        }
    }

}
