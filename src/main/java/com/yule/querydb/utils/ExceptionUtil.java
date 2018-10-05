package com.yule.querydb.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 * @author yule
 * @date 2018/10/5 19:47
 */
public class ExceptionUtil {
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
