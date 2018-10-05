package com.yule.querydb.component.dbcomponent.service;

import com.yule.querydb.annotation.MyParam;
import com.yule.querydb.component.dbcomponent.entity.UserColComments;
import com.yule.querydb.component.dbcomponent.entity.UserTables;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yule
 * @date 2018/10/1 22:10
 */
public interface DbComponentTopService {

    /**
     * 查询数据源
     *
     * @return
     */
    List<String> getDbComponentDataSources();

    /**
     * 查询所有表名，有限制私密
     *
     * @return
     */
    List<UserTables> selectUserTablesListByTbName(@MyParam("tableName") String tableName, @MyParam("dataSourceType") String dataSourceType);

    /**
     * 查询某表的所有列字段，限制私密
     *
     * @return
     */
    List<UserColComments> selectUserColCommentsListByTbName(@MyParam("tableName") String tableName, @MyParam("dataSourceType") String dataSourceType);

    /**
     * 根据表名获取表格数据，有限制私密
     *
     * @param tableName           表名
     * @param tableConditionsJson 表的查询条件
     * @return
     */
    Object getTableData(@MyParam("tableName") String tableName, @MyParam("tableConditionsJson") String tableConditionsJson,
                        @MyParam("dataSourceType") String dataSourceType,
                        @MyParam("start") Integer start, @MyParam("limit") Integer limit,
                        @MyParam("field") String field, @MyParam("direction") String direction);

    /**
     * 导出excel
     *
     * @param request
     * @param response
     * @param tableName
     * @param tableConditionsJson
     * @param dataSourceType
     * @param field
     * @param direction
     */
    void exportExcelTableData(@MyParam("httpServletRequest") HttpServletRequest request, @MyParam("httpServletResponse") HttpServletResponse response,
                              @MyParam("tableName") String tableName, @MyParam("tableConditionsJson") String tableConditionsJson,
                              @MyParam("dataSourceType") String dataSourceType,
                              @MyParam("field") String field, @MyParam("direction") String direction);
}
