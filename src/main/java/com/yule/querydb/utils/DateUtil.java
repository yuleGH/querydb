package com.yule.querydb.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具类
 * @author yule
 * @date 2018/10/5 16:56
 */
public class DateUtil {

    public static final String FMT_YYYYMMDD = "yyyyMMdd";

    /**
     * 获取当前时间
     * @param format
     * @return
     */
    public static String getNow(String format) {
        SimpleDateFormat tempDate = new SimpleDateFormat(format);
        return tempDate.format(new Date());
    }
}
