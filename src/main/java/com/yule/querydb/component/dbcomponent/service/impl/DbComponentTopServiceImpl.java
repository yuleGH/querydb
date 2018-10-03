package com.yule.querydb.component.dbcomponent.service.impl;

import com.yule.querydb.component.dbcomponent.entity.UserColComments;
import com.yule.querydb.component.dbcomponent.entity.UserTables;
import com.yule.querydb.component.dbcomponent.service.DbComponentService;
import com.yule.querydb.component.dbcomponent.service.DbComponentTopService;
import com.yule.querydb.datasource.DataSourceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库单表查询组件
 * 需要解决问题：
 *      私密性：后台需要有个 json 文件设置哪些表能查，哪些表的字段不能查；（指定表、指定字段不查）
 *      支持导出功能
 *      通用性组件：弄成 jar 包，包含前端（druid alibaba 数据库连接池，做成jar包）
 *      日期查询条件处理：时间类型的直接给时间段，年月日时分秒
 *      考虑返回 Map 后的字段类型处理（现支持：NUMBER,VARCHAR,TIMESTAMP,DATA）
 *      考虑多个数据库配置
 *      注意不能造成sql注入
 *      查询条件动态组装，isnull,isnotnull,like...     防止sql注入      可以参考老潘的东西
 *      后续支持不同数据库，mybatis、mysql、oracle
 *
 * @author yule
 * @date 2018/9/22 15:57
 */
public class DbComponentTopServiceImpl implements DbComponentTopService {

    @Autowired
    private DbComponentService dbComponentService;

    @Value("${dbComponentDataSources}")
    private String dbComponentDataSources;

    /**
     * 查询数据源
     * @return
     */
    @Override
    public List<String> getDbComponentDataSources(){
        String[] dataSourceArray = dbComponentDataSources.split(",");
        return Arrays.asList(dataSourceArray);
    }

    /**
     * 查询所有表名，有限制私密
     * @return
     */
    @Override
    public List<UserTables> selectUserTablesListByTbName(String tableName, String dataSourceType){
        //todo 校验
        DataSourceHolder.setDataSourceType(dataSourceType);
        List<UserTables> userTablesList = this.dbComponentService.selectUserTablesListByTbName(tableName);
        return userTablesList;
    }

    /**
     * 查询某表的所有列字段，限制私密
     * @return
     */
    @Override
    public List<UserColComments> selectUserColCommentsListByTbName(String tableName, String dataSourceType){
        DataSourceHolder.setDataSourceType(dataSourceType);
        List<UserColComments> userColCommentsList = this.dbComponentService.selectUserColCommentsListByTbName(tableName);
        return userColCommentsList;
    }

    /**
     * 根据表名获取表格数据，有限制私密
     * @param tableName 表名
     * @param tableConditionsJson 表的查询条件
     * @return
     */
    @Override
    public Object getTableData(String tableName, String tableConditionsJson, String dataSourceType,
                               Integer start, Integer limit, String field, String direction){
        DataSourceHolder.setDataSourceType(dataSourceType);

        Map<String, Object> pageConfMap = new HashMap<>(16);
        pageConfMap.put("start", start == null ? 0 : start);
        pageConfMap.put("limit", limit == null ? 0 : limit);
        pageConfMap.put("field", field);
        pageConfMap.put("direction", direction);
        List<Map<String, String>> tableData = this.dbComponentService.getTableData(tableName, tableConditionsJson, pageConfMap);
        int totalCount = 0;
        if(tableData.size() > 0){
            totalCount = this.dbComponentService.getTableDataCount(tableName, tableConditionsJson);
        }

        Map result = new HashMap<>(2);
        result.put("pageData", tableData);
        result.put("total", totalCount);
        return result;
    }
}
