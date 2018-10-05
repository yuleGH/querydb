package com.yule.querydb.component.dbcomponent.entity;

import java.util.List;

/**
 * 解析配置文件 json 文件
 * @author yule
 * @date 2018/9/28 17:43
 */
public class LimitInfo {
    /**
     * 可以查询的表名数组
     */
    private List<String> canQueryTables;

    /**
     * 禁止查询的字段对象
     */
    private List<LimitColumnInfo> forbidQueryColumns;

    public List<String> getCanQueryTables() {
        return canQueryTables;
    }

    public void setCanQueryTables(List<String> canQueryTables) {
        this.canQueryTables = canQueryTables;
    }

    public List<LimitColumnInfo> getForbidQueryColumns() {
        return forbidQueryColumns;
    }

    public void setForbidQueryColumns(List<LimitColumnInfo> forbidQueryColumns) {
        this.forbidQueryColumns = forbidQueryColumns;
    }
}
