package com.yule.querydb.utils;

import java.io.InputStream;

/**
 * IO 工具类
 * @author yule
 * @date 2018/10/1 16:48
 */
public class IoUtil {

    /**
     * 关闭流
     * @param in
     */
    public static void close(InputStream in) {
        if (in == null) {
            return;
        }

        try {
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
