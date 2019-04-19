package com.soaring.eagle.common.constant;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/21
 * Time: 8:33
 */
public class SpecialResultModel<T> extends ResultModel<T> {

    /**
     * 特殊值
     */
    private T special;

    public SpecialResultModel(String value, String message, T data, T special) {
        super(value, message, data);
        this.special = special;
    }

    public SpecialResultModel(ResultStatus resultStatus, T data, T special) {
        super(resultStatus, data);
        this.special = special;
    }

    public T getSpecial() {
        return special;
    }

    public void setSpecial(T special) {
        this.special = special;
    }
}
