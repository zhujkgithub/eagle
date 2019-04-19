package com.soaring.eagle.common.constant;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/20
 * Time: 15:45
 */
public enum ResultStatus {

    /**
     * 成功
     */
    SUCCESS("0", "成功"),

    /**
     * 失败
     */
    FAIL("1", "失败");

    /**
     * 状态值
     */
    private String value;

    /**
     * 状态信息
     */
    private String message;

    ResultStatus(String value, String message) {
        this.value = value;
        this.message = message;
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
}
