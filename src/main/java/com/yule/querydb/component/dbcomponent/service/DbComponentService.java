package com.yule.querydb.component.dbcomponent.service;

import com.yule.querydb.annotation.MyParam;
import com.yule.querydb.component.dbcomponent.entity.UserColComments;
import com.yule.querydb.component.dbcomponent.entity.UserTables;

import java.util.List;
import java.util.Map;

/**
 * @author yule
 * @date 2018/9/22 15:57
 */
public interface DbComponentService {

    /**
     * 查询所有的表名
     * @param tableName
     * @return
     */
    List<UserTables> selectUserTablesListByTbName(String tableName);

    /**
     * 查询某张表的所有字段
     * @param tableName
     * @return
     */
    List<UserColComments> selectUserColCommentsListByTbName(String tableName);

    /**
     * 根据表名获取表格数据
     * @param tableName
     * @param tableConditionsJson
     * @param pageConfMap
     * @return
     */
    List<Map<String, String>> getTableData(String tableName, String tableConditionsJson, Map<String, Object> pageConfMap);

    /**
     * 数量
     * @param tableName
     * @param tableConditionsJson
     * @return
     */
    int getTableDataCount(String tableName, String tableConditionsJson);
}
