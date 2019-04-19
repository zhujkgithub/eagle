package com.soaring.eagle.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/21
 * Time: 9:06
 */
public class DateUtils {

    /**
     *
     * @param stringDate 待转换的日期格式字符串
     * @param format     待转换的日期格式
     * @return 日期格式的字符串转日期
     */
    public static Date stringToDate(String stringDate, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException e) {
            return null;
        }
    }

}
