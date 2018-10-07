package com.yule.querydb.component.dbcomponent.entity;

/**
 * 用户自定义查询条件
 * @author yule
 * @date 2018/10/7 20:57
 */
public class CustomCondition {
    /**
     * 列名
     */
    private String columnName;

    /**
     * 查询类型的枚举Code
     */
    private String customConditionEnumCode;

    /**
     * 列值，普通输入框的值
     */
    private String columnVal;
    /**
     * 时间段-查询条件：开始时间
     */
    private String startDateVal;
    /**
     * 时间段-查询条件：结束时间
     */
    private String endDateVal;

    /**
     * in 、notIn 的值
     */
    private String[] valArray;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCustomConditionEnumCode() {
        return customConditionEnumCode;
    }

    public void setCustomConditionEnumCode(String customConditionEnumCode) {
        this.customConditionEnumCode = customConditionEnumCode;
    }

    public String getColumnVal() {
        return columnVal;
    }

    public void setColumnVal(String columnVal) {
        this.columnVal = columnVal;
    }

    public String getStartDateVal() {
        return startDateVal;
    }

    public void setStartDateVal(String startDateVal) {
        this.startDateVal = startDateVal;
    }

    public String getEndDateVal() {
        return endDateVal;
    }

    public void setEndDateVal(String endDateVal) {
        this.endDateVal = endDateVal;
    }

    public String[] getValArray() {
        return valArray;
    }

    public void setValArray(String[] valArray) {
        this.valArray = valArray;
    }
}
