package com.yule.querydb.component.dbcomponent.service.impl;

import com.yule.querydb.commonenum.CustomConditionEnum;
import com.yule.querydb.component.dbcomponent.entity.UserColComments;
import com.yule.querydb.component.dbcomponent.entity.UserTables;
import com.yule.querydb.component.dbcomponent.service.DbComponentService;
import com.yule.querydb.component.dbcomponent.service.DbComponentTopService;
import com.yule.querydb.component.dbcomponent.utils.DbDataSourceNameUtil;
import com.yule.querydb.datasource.DataSourceHolder;
import com.yule.querydb.utils.CommonUtil;
import com.yule.querydb.utils.excel.ExportExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 数据库单表查询组件
 * 需要解决问题：
 *      私密性：后台需要有个 json 文件设置哪些表能查，哪些表的字段不能查；（指定表、指定字段不查）
 *      通用性组件：弄成 jar 包，包含前端（druid alibaba 数据库连接池，做成jar包）
 *      考虑返回 Map 后的字段类型处理（现支持：NUMBER,VARCHAR,TIMESTAMP,DATA）
 *      考虑多个数据库配置
 *
 *      日期查询条件处理：时间类型的直接给时间段，年月日时分秒
 *      支持导出功能
 *      注意不能造成sql注入
 *      查询条件动态组装，isnull,isnotnull,like...     防止sql注入      可以参考老潘的东西
 *      后续支持不同数据库，mybatis、mysql、oracle
 *
 * @author yule
 * @date 2018/9/22 15:57
 */
public class DbComponentTopServiceImpl implements DbComponentTopService {

    private final Logger logger = LoggerFactory.getLogger(DbComponentTopServiceImpl.class);

    @Autowired
    private DbComponentService dbComponentService;

    @Override
    public Object getDbComponentDataSources(){
        return DbDataSourceNameUtil.getDbDataSourceNamesMap();
    }

    @Override
    public List<UserTables> selectUserTablesListByTbName(String tableName, String dataSourceType){
        DataSourceHolder.setDataSourceType(dataSourceType);
        List<UserTables> userTablesList = this.dbComponentService.selectUserTablesListByTbName(tableName);
        return userTablesList;
    }

    @Override
    public List<UserColComments> selectUserColCommentsListByTbName(String tableName, String dataSourceType){
        DataSourceHolder.setDataSourceType(dataSourceType);
        List<UserColComments> userColCommentsList = this.dbComponentService.selectUserColCommentsListByTbName(tableName);
        return userColCommentsList;
    }

    @Override
    public Object getTableData(String tableName, String tableConditionsJson, String customConditionsJson, String dataSourceType,
                               Integer start, Integer limit, String field, String direction){
        DataSourceHolder.setDataSourceType(dataSourceType);

        List<Map<String, String>> tableData = getTableDataList(start, limit, field, direction, tableName, tableConditionsJson, customConditionsJson);
        int totalCount = 0;
        if(tableData.size() > 0){
            totalCount = this.dbComponentService.getTableDataCount(tableName, tableConditionsJson, customConditionsJson);
        }

        Map result = new HashMap<>(2);
        result.put("pageData", tableData);
        result.put("total", totalCount);
        return result;
    }

    @Override
    public void exportExcelTableData(HttpServletRequest request, HttpServletResponse response,
                                     String tableName, String tableConditionsJson, String customConditionsJson, String dataSourceType,
                                     String field, String direction) {
        DataSourceHolder.setDataSourceType(dataSourceType);

        List<String> headerList = new ArrayList<>();
        List<String> fieldList = new ArrayList<>();

        List<UserColComments> userColCommentsList = this.dbComponentService.selectUserColCommentsListByTbName(tableName);
        if(CommonUtil.isNotNullOrBlock(userColCommentsList)){
            for(UserColComments userColComments : userColCommentsList){
                headerList.add(userColComments.getColumnName() + "：" + userColComments.getComments());
                fieldList.add(userColComments.getColumnName());
            }
        }

        List<Map<String, String>> tableData = getTableDataList(0, Integer.MAX_VALUE, field, direction, tableName, tableConditionsJson, customConditionsJson);

        try {
            ExportExcelUtil.exportExcel(request, response, tableName, headerList, fieldList, tableData);
        } catch (Exception e) {
            logger.error("导出报错！", e);
        }
    }

    @Override
    public Map<String, String> getAllCustomConditionEnum() {
        Map<String, String> allEnumMap = new HashMap<>();
        List<CustomConditionEnum> customConditionEnumList = CustomConditionEnum.getAllEnum();
        for(CustomConditionEnum customConditionEnum : customConditionEnumList){
            allEnumMap.put(customConditionEnum.getCode(), customConditionEnum.getValue());
        }

        return allEnumMap;
    }

    /**
     * 只获取表格数据
     * @param start
     * @param limit
     * @param field
     * @param direction
     * @param tableName
     * @param tableConditionsJson
     * @return
     */
    private List<Map<String, String>> getTableDataList(Integer start, Integer limit, String field, String direction, String tableName,
                                                       String tableConditionsJson, String customConditionsJson) {
        Map<String, Object> pageConfMap = new HashMap<>(16);
        pageConfMap.put("start", start == null ? 0 : start);
        pageConfMap.put("limit", limit == null ? 0 : limit);
        pageConfMap.put("field", field);
        pageConfMap.put("direction", direction);
        List<Map<String, String>> tableData = this.dbComponentService.getTableData(tableName, tableConditionsJson, customConditionsJson, pageConfMap);
        return tableData;
    }

}
