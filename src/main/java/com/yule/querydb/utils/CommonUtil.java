package com.yule.querydb.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 公共的工具类
 * @author yule
 * @date 2018/10/1 17:54
 */
public class CommonUtil {

    /**
     * 字符串为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(str == null){
            return true;
        }
        return "".equals(str.trim());
    }

    /**
     * 字符串不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpth(String str){
        return !isEmpty(str);
    }

    /**
     * 判断对象是否为空
     * @param obj
     * @return
     */
    public static boolean isNullOrBlock(Object obj){
        if(obj != null && !obj.toString().equals("")){
            if(obj instanceof Object[]){
                Object[] obj1 = (Object[]) obj;
                return 0 == obj1.length;
            }else if(obj instanceof Collection){
                Collection collection = (Collection) obj;
                return 0 == collection.size();
            }else if(obj instanceof Map){
                Map map = (Map) obj;
                return 0 == map.size();
            }else{
                return false;
            }
        }else{
            return true;
        }
    }

    /**
     * 对象不为空
     * @param obj
     * @return
     */
    public static boolean isNotNullOrBlock(Object obj){
        return !isNullOrBlock(obj);
    }

}
