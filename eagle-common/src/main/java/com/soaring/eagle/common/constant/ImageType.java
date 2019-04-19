package com.soaring.eagle.common.constant;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/29
 * Time: 16:53
 */
public enum ImageType implements Serializable {
    /**
     * 图片
     */
    PHOTO("photo"),
    /**
     * 图标
     */
    ICON("icon"),
    /**
     * 其他
     */
    OTHER("other"),
    /**
     * logo
     */
    LOGO("logo");

    private String type;

    ImageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
