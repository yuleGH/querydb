package com.yule.querydb.commonenum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 查询条件的方式，便于封装
 * @author yule
 * @date 2018/9/30 10:47
 */
public enum QueryConditionEnum {
    /*查询方式*/
    isNull("isNull", "isNull"),
    isNotNull("isNotNull", "isNotNull"),
    equalTo("equalTo", "equalTo"),
    notEqualTo("notEqualTo", "notEqualTo"),
    greaterThan("greaterThan", "greaterThan"),
    greaterThanOrEqualTo("greaterThanOrEqualTo", "greaterThanOrEqualTo"),
    lessThan("lessThan", "lessThan"),
    lessThanOrEqualTo("lessThanOrEqualTo", "lessThanOrEqualTo"),
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
    QueryConditionEnum(String code, String value){
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
    public static QueryConditionEnum getByCode(String code){
        for(QueryConditionEnum _enum : values()){
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
    public static List<QueryConditionEnum> getAllEnum(){
        List<QueryConditionEnum> queryConditionEnums = Arrays.asList(values());
        List<QueryConditionEnum> list = new ArrayList<>(queryConditionEnums);
        return list;
    }

    /**
     * 获取全部枚举code
     * @return
     */
    public static List<String> getAllEnumCode(){
        List<String> list = new ArrayList<>();
        for(QueryConditionEnum _enum : values()){
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
        for(QueryConditionEnum _enum : values()){
            list.add(_enum.value);
        }
        return list;
    }

}
