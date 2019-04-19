package com.soaring.eagle.common.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/4/1
 * Time: 11:20
 * description: 自定义封装分页参数，需要前台参数，再转为MyBatis Page对象
 */
public class PageParam {
    public static final String DESC = "DESC";
    public static final String ASC = "ASC";
    private int pageSize;
    private int pageNumber;
    private String sortName;
    private String sortOrder;
    private boolean searchCount = true;
    private Map<String, Object> params = new HashMap<>();

    public PageParam() {
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSortName() {
        return this.sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isSearchCount() {
        return this.searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
