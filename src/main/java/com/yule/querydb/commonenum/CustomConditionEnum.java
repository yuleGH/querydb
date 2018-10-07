package com.yule.querydb.commonenum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 查询条件的方式，便于封装
 * @author yule
 * @date 2018/9/30 10:47
 */
public enum CustomConditionEnum {
    /*查询方式*/
    isNull("isNull", "isNull"),
    isNotNull("isNotNull", "isNotNull"),
    equalTo("equalTo", "等于"),
    notEqualTo("notEqualTo", "不等于"),
    greaterThan("greaterThan", "大于"),
    greaterThanOrEqualTo("greaterThanOrEqualTo", "大于等于"),
    lessThan("lessThan", "小于"),
    lessThanOrEqualTo("lessThanOrEqualTo", "小于等于"),
    in("in", "in"),
    notIn("notIn", "notIn"),
    between("between", "between"),
    notBetween("notBetween", "notBetween"),
    like("like", "like"),
    notLike("notLike", "notLike");

    /*枚举值*/
    final String code;
    /*枚举描述*/
    final String value;

    /**
     * 构造一个枚举对象
     * @param code
     * @param value
     */
    CustomConditionEnum(String code, String value){
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    /**
     * 通过枚举值获得枚举对象
     * @param code
     * @return
     */
    public static CustomConditionEnum getByCode(String code){
        for(CustomConditionEnum _enum : values()){
            if(_enum.getCode().equals(code)){
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举对象
     * @return
     */
    public static List<CustomConditionEnum> getAllEnum(){
        List<CustomConditionEnum> customConditionEnums = Arrays.asList(values());
        List<CustomConditionEnum> list = new ArrayList<>(customConditionEnums);
        return list;
    }

    /**
     * 获取全部枚举code
     * @return
     */
    public static List<String> getAllEnumCode(){
        List<String> list = new ArrayList<>();
        for(CustomConditionEnum _enum : values()){
            list.add(_enum.getCode());
        }
        return list;
    }

    /**
     * 获取全部枚举value
     * @return
     */
    public static List<String> getAllEnumValue(){
        List<String> list = new ArrayList<>();
        for(CustomConditionEnum _enum : values()){
            list.add(_enum.value);
        }
        return list;
    }

}
