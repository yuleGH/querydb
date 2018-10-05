package com.yule.querydb.component.dbcomponent.entity;

import java.util.List;

/**
 * 限制查询的字段
 * @author yule
 * @date 2018/10/5 15:38
 */
public class LimitColumnInfo {
    private String tableName;
    private List<String> tableColumns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(List<String> tableColumns) {
        this.tableColumns = tableColumns;
    }
}
