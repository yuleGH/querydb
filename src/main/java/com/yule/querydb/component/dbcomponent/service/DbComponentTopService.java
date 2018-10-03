package com.yule.querydb.component.dbcomponent.service;

import com.yule.querydb.annotation.MyParam;
import com.yule.querydb.component.dbcomponent.entity.UserColComments;
import com.yule.querydb.component.dbcomponent.entity.UserTables;

import java.util.List;

/**
 * @author yule
 * @date 2018/10/1 22:10
 */
public interface DbComponentTopService {

    List<String> getDbComponentDataSources();

    List<UserTables> selectUserTablesListByTbName(@MyParam("tableName") String tableName, @MyParam("dataSourceType") String dataSourceType);


    List<UserColComments> selectUserColCommentsListByTbName(@MyParam("tableName") String tableName, @MyParam("dataSourceType") String dataSourceType);

    Object getTableData(@MyParam("tableName") String tableName, @MyParam("tableConditionsJson") String tableConditionsJson, @MyParam("dataSourceType") String dataSourceType,
                        @MyParam("start") Integer start, @MyParam("limit") Integer limit, @MyParam("field") String field, @MyParam("direction") String direction);
}
