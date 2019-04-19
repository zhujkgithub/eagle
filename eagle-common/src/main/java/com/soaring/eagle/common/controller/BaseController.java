package com.soaring.eagle.common.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.soaring.eagle.common.constant.ResultModel;
import com.soaring.eagle.common.util.FastJsonUtils;
import com.soaring.eagle.common.util.SnowFlakeWorkerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/22
 * Time: 12:59
 */
public class BaseController {

    /**
     * hibernate校验错误信息放入Map中
     *
     * @param bindingResult
     * @return
     */
    protected String getAllErrors(BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>(16);
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                FieldError fieldError = (FieldError) objectError;
                map.put(fieldError.getField(), objectError.getDefaultMessage());
            }
        }
        return FastJsonUtils.toJSONString(map);
    }

    /**
     * 得到分页对象中 records
     *
     * @param resultModel
     * @return
     */
    protected Object getRecords(ResultModel resultModel) {
        Object data = resultModel.getData();
        Map map = FastJsonUtils.toBean(FastJsonUtils.toJSONNoConfig(data), Map.class);
        return map.get("records");
    }

    /**
     * 转化为MyBatis Page对象
     *
     * @param pageParam
     * @return
     */
    protected Page getPage(PageParam pageParam) {
        Page result = new Page(pageParam.getPageNumber(), pageParam.getPageSize());
        result.setCondition(pageParam.getParams());
        if (StringUtils.isNotBlank(pageParam.getSortName())) {
            result.setOrderByField(pageParam.getSortName());
            result.setOpenSort(true);
        }

        if (StringUtils.isNotBlank(pageParam.getSortOrder()) && "DESC".equalsIgnoreCase(pageParam.getSortOrder())) {
            result.setAsc(false);
        }

        if (!pageParam.isSearchCount()) {
            result.setSearchCount(pageParam.isSearchCount());
        }

        return result;
    }

    /**
     * 生成 bigint主键
     * 只要15位，JS 中能精准表示的最大整数是 Math.pow(2, 53)，十进制即 9007199254740992
     *
     * @return 主键
     */
    protected Long getLongId() {
        SnowFlakeWorkerUtil instance = SnowFlakeWorkerUtil.getInstance();
        String longId = String.valueOf(instance.nextId());
        return Long.parseLong(longId.substring(longId.length() - 15));
    }

    /**
     * 返回boolean值时使用
     *
     * @param flag
     * @return
     */
    protected ResultModel basicResultModel(Boolean flag) {
        return flag ? ResultModel.successResultModel() : ResultModel.failResultModel();
    }

}
