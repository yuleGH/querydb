package com.yule.querydb.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * response contentType 类型
 * @author yule
 * @date 2018/10/5 13:42
 */
public class ContentTypeUtil {

    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    private static final Map<String, String> CONTENT_TYPE_MAP = new HashMap<String, String>(){};

    static {
        CONTENT_TYPE_MAP.put(".html", "text/html; charset=utf-8");
        CONTENT_TYPE_MAP.put(".css", "text/css;charset=utf-8");
        CONTENT_TYPE_MAP.put(".js", "text/javascript;charset=utf-8");
        CONTENT_TYPE_MAP.put(".json", "application/json");

        //字体
        CONTENT_TYPE_MAP.put(".ttf", "application/x-font-ttf");
        CONTENT_TYPE_MAP.put(".woff", "application/x-font-woff");

        //图片
        CONTENT_TYPE_MAP.put(".jpg", "image/jpeg");
        CONTENT_TYPE_MAP.put(".png", "image/png");
    }

    /**
     * 判断是否包含key
     * @param key
     * @return
     */
    private static boolean containsKey(String key){
        return CONTENT_TYPE_MAP.containsKey(key);
    }

    /**
     * 通过key获取值
     * @param key
     * @return
     */
    public static String getValueByKey(String key){
        if(containsKey(key)){
            return CONTENT_TYPE_MAP.get(key);
        }else{
            return DEFAULT_CONTENT_TYPE;
        }
    }

    /**
     * 获取默认值
     * @return
     */
    public static String getDefaultValue() {
        return DEFAULT_CONTENT_TYPE;
    }
}
