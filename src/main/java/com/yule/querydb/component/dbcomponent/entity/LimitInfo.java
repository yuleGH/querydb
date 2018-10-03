package com.yule.querydb.component.dbcomponent.entity;

import java.util.List;

/**
 * 解析配置文件 json 文件
 * @author yule
 * @date 2018/9/28 17:43
 */
public class LimitInfo {
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
