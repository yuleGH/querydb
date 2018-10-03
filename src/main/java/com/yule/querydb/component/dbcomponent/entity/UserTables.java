package com.yule.querydb.component.dbcomponent.entity;

/**
 * 用户所有表
 * @author yule
 * @date 2018/9/22 15:30
 */
public class UserTables {
    private String tableName;
    /**
     * 表注释
     */
    private String comments;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
