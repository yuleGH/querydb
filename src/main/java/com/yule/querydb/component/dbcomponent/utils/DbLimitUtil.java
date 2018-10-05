package com.yule.querydb.component.dbcomponent.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yule.querydb.component.dbcomponent.entity.LimitColumnInfo;
import com.yule.querydb.component.dbcomponent.entity.LimitInfo;
import com.yule.querydb.datasource.DataSourceHolder;
import com.yule.querydb.utils.CommonUtil;
import com.yule.querydb.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库查询限制
 *
 * @author yule
 * @date 2018/9/28 15:58
 */
public class DbLimitUtil {
    private static Map<String, String> dataSourceLimitJsonUrlMap = buildDataSourceLimitJsonUrlMap();

    private static final Logger logger = LoggerFactory.getLogger(DbLimitUtil.class);

    /**
     * 初始化  dataSourceLimitJsonUrlMap
     * @return
     */
    private static Map<String, String> buildDataSourceLimitJsonUrlMap() {
        Map<String, String> dataSourceLimitJsonUrlMap = new HashMap<>(4);

        String dbComponentDataSources = PropertiesUtils.getValue("dbComponentDataSources");
        String dbComponentDataSourceLimitJsonUrls = PropertiesUtils.getValue("dbComponentDataSourceLimitJsonUrls");
        if(CommonUtil.isEmpty(dbComponentDataSources)){
            logger.error("缺少配置 dbComponentDataSources！请配置后重试。");
        }
        if(CommonUtil.isEmpty(dbComponentDataSourceLimitJsonUrls)){
            logger.error("缺少配置 dbComponentDataSourceLimitJsonUrls！请配置后重试。");
        }

        String[] dbComponentDataSourceArray = dbComponentDataSources.split(",");
        String[] dbComponentDataSourceLimitJsonUrlArray = dbComponentDataSourceLimitJsonUrls.split(",");
        for(int i = 0; i < dbComponentDataSourceArray.length; i++){
            dataSourceLimitJsonUrlMap.put(dbComponentDataSourceArray[i].trim(), dbComponentDataSourceLimitJsonUrlArray[i].trim());
        }
        return dataSourceLimitJsonUrlMap;
    }

    /**
     * 通过数据源类型来查找 限制文件，并返回限制类
     * @return
     */
    private static LimitInfo getLimitInfo(){
        String json = PropertiesUtils.readJsonFile(dataSourceLimitJsonUrlMap.get(DataSourceHolder.getDataSourceType()));

        Gson gson = new Gson();
        LimitInfo limitInfo = gson.fromJson(json, new TypeToken<LimitInfo>(){}.getType());

        limitInfoListToUpperCase(limitInfo);

        return limitInfo;
    }

    /**
     * 值全大写
     * @param limitInfo
     */
    private static void limitInfoListToUpperCase(LimitInfo limitInfo) {
        //转换表名为大写
        limitInfo.setCanQueryTables(strListToUpperCase(limitInfo.getCanQueryTables()));

        //转换禁止的表字段为大写
        if(CommonUtil.isNotNullOrBlock(limitInfo.getForbidQueryColumns())){
            List<LimitColumnInfo> newLimitColumnInfoList = new ArrayList<>();
            for(LimitColumnInfo limitColumnInfo : limitInfo.getForbidQueryColumns()){
                if(CommonUtil.isEmpty(limitColumnInfo.getTableName()) || CommonUtil.isNullOrBlock(limitColumnInfo.getTableColumns())){
                    continue;
                }

                limitColumnInfo.setTableName(limitColumnInfo.getTableName().toUpperCase());
                limitColumnInfo.setTableColumns(strListToUpperCase(limitColumnInfo.getTableColumns()));

                newLimitColumnInfoList.add(limitColumnInfo);
            }
            limitInfo.setForbidQueryColumns(newLimitColumnInfoList);
        }

    }

    /**
     * 将String list 转为大写
     * @param list
     * @return
     */
    private static List<String> strListToUpperCase(List<String> list) {
        List<String> newList = new ArrayList<>();

        if(CommonUtil.isNullOrBlock(list)){
            return newList;
        }

        for(String str : list){
            newList.add(CommonUtil.isEmpty(str) ? str : str.toUpperCase());
        }
        return newList;
    }

    /**
     * 获取可以查询的整个表的表名List
     * @return
     */
    public static List<String> getCanQueryTableNameList(){
        LimitInfo limitInfo = getLimitInfo();
        return limitInfo.getCanQueryTables();
    }

    /**
     * 获取某表下的限制列
     * @param tableName
     * @return
     */
    public static List<String> getForbidTableColumnListByTableName(String tableName){
        List<String> tableColumnLimitList = new ArrayList<>();

        if(CommonUtil.isEmpty(tableName)){
            return tableColumnLimitList;
        }

        tableName = tableName.toUpperCase();

        Map<String, List<String>> forbidQueryColumnsMap = getForbidQueryColumnsMap();
        if(!forbidQueryColumnsMap.containsKey(tableName)){
            return tableColumnLimitList;
        }

        return forbidQueryColumnsMap.get(tableName);

    }

    /**
     * 获取表名的限制字段map
     * @return
     */
    private static Map<String, List<String>> getForbidQueryColumnsMap(){
        Map<String, List<String>> forbidQueryColumnsMap = new HashMap<>(16);

        LimitInfo limitInfo = getLimitInfo();
        List<LimitColumnInfo> forbidQueryColumns = limitInfo.getForbidQueryColumns();
        if(CommonUtil.isNotNullOrBlock(forbidQueryColumns)){
            for(LimitColumnInfo limitColumnInfo : forbidQueryColumns){
                forbidQueryColumnsMap.put(limitColumnInfo.getTableName(), limitColumnInfo.getTableColumns());
            }
        }
        return forbidQueryColumnsMap;
    }
}
