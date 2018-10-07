package com.yule.querydb.component.dbcomponent.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yule.querydb.commonenum.CustomConditionEnum;
import com.yule.querydb.component.dbcomponent.dao.UserColCommentsDao;
import com.yule.querydb.component.dbcomponent.dao.UserTablesDao;
import com.yule.querydb.component.dbcomponent.entity.CustomCondition;
import com.yule.querydb.component.dbcomponent.entity.UserColComments;
import com.yule.querydb.component.dbcomponent.entity.UserTables;
import com.yule.querydb.component.dbcomponent.service.DbComponentService;
import com.yule.querydb.component.dbcomponent.utils.DbLimitUtil;
import com.yule.querydb.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yule
 * @date 2018/9/22 15:57
 */
public class DbComponentServiceImpl implements DbComponentService {
    @Autowired
    private UserTablesDao userTablesDao;
    @Autowired
    private UserColCommentsDao userColCommentsDao;

    private Logger logger = LoggerFactory.getLogger(DbComponentServiceImpl.class);

    @Override
    public List<UserTables> selectUserTablesListByTbName(String tableName) {
        List<String> canQueryTableNameList = DbLimitUtil.getCanQueryTableNameList();
        return this.userTablesDao.selectUserTablesListByTbName(tableName, canQueryTableNameList);
    }

    @Override
    public List<UserColComments> selectUserColCommentsListByTbName(String tableName){
        return getAllTableColCommentsListByDelLimit(tableName);
    }

    @Override
    public List<Map<String, String>> getTableData(String tableName, String tableConditionsJson, String customConditionsJson, Map<String, Object> pageConfMap) {
        if(CommonUtil.isEmpty(tableName)){
            return new ArrayList<>(0);
        }
        //防止 sql 注入，校验表名
        List<UserTables> userTableList = this.selectUserTablesListByTbName(tableName);
        if(userTableList == null || userTableList.size() == 0){
            logger.error("表名有问题！");
            return new ArrayList<>(0);
        }

        //转查询条件，防止 sql 注入，校验列名
        Map<String, UserColComments> allUserColCommentsMap = getAllUserColCommentsMapByTableName(tableName);
        List<UserColComments> colConditionList = convertTableConditionsJsonToList(allUserColCommentsMap, tableConditionsJson);
        List<CustomCondition> customConditionList = convertCustomConditionsJsonToList(allUserColCommentsMap, customConditionsJson);

        //查询表格数据
        pageConfMap.put("tableName", tableName);
        pageConfMap.put("colConditionList", colConditionList);
        pageConfMap.put("customConditionList", customConditionList);
        pageConfMap.put("allTableColCommentsListByDelLimit", getAllTableColCommentsListByDelLimit(tableName));
        return this.userTablesDao.selectTableDataByConditions(pageConfMap);
    }

    /**
     * 根据表名获取所有可以查的列名map
     * @param tableName
     * @return
     */
    private Map<String, UserColComments> getAllUserColCommentsMapByTableName(String tableName) {
        List<String> tableColumnLimitList = DbLimitUtil.getForbidTableColumnListByTableName(tableName);
        List<UserColComments> allUserColCommentsList = this.userColCommentsDao.selectUserColCommentsListByTbName(tableName, tableColumnLimitList);
        Map<String, UserColComments> allUserColCommentsMap = new HashMap<>();
        for(UserColComments userColComments : allUserColCommentsList){
            allUserColCommentsMap.put(userColComments.getColumnName(), userColComments);
        }
        return allUserColCommentsMap;
    }

    /**
     * 转换用户自定义的查询条件为list
     * @param allUserColCommentsMap
     * @param customConditionsJson
     * @return
     */
    private List<CustomCondition> convertCustomConditionsJsonToList(Map<String, UserColComments> allUserColCommentsMap, String customConditionsJson) {
        List<CustomCondition> result = new ArrayList<>();
        if(!CommonUtil.isEmpty(customConditionsJson)){
            Gson gson = new Gson();
            List<CustomCondition> customConditionList = gson.fromJson(customConditionsJson, new TypeToken<List<CustomCondition>>(){}.getType());

            for(CustomCondition customCondition : customConditionList){
                if(CommonUtil.isNotEmpth(customCondition.getColumnName()) && !allUserColCommentsMap.containsKey(customCondition.getColumnName())){
                    logger.error("列名不正确：{}", customCondition.getColumnName());
                    return null;
                }

                //处理用户输入的in和notIn
                if((CustomConditionEnum.in.getCode().equals(customCondition.getCustomConditionEnumCode()) || CustomConditionEnum.notIn.getCode().equals(customCondition.getCustomConditionEnumCode()))
                        && CommonUtil.isNotEmpth(customCondition.getColumnVal())){
                    String columnVal = customCondition.getColumnVal().replace("，", ",");
                    customCondition.setValArray(columnVal.split(","));
                    customCondition.setColumnVal(null);
                }

                if(CommonUtil.isNotEmpth(customCondition.getColumnName()) && CommonUtil.isNotEmpth(customCondition.getCustomConditionEnumCode())){
                    result.add(customCondition);
                }
            }
        }

        return result;
    }

    /**
     * 转换查询条件为list
     * @param allUserColCommentsMap
     * @param tableConditionsJson
     * @return
     */
    private List<UserColComments> convertTableConditionsJsonToList(Map<String, UserColComments> allUserColCommentsMap, String tableConditionsJson) {
        List<UserColComments> colConditionList = null;
        if(!CommonUtil.isEmpty(tableConditionsJson)){
            Gson gson = new Gson();
            colConditionList = gson.fromJson(tableConditionsJson, new TypeToken<List<UserColComments>>(){}.getType());

            for(UserColComments userColComments : colConditionList){
                if(!allUserColCommentsMap.containsKey(userColComments.getColumnName())){
                    logger.error("列名不正确：{}", userColComments.getColumnName());
                    return null;
                }
            }
        }
        return colConditionList;
    }

    /**
     * 获取表里所有的字段，排除不能显示的
     * @param tableName
     * @return
     */
    private List<UserColComments> getAllTableColCommentsListByDelLimit(String tableName) {
        List<String> tableColumnLimitList = DbLimitUtil.getForbidTableColumnListByTableName(tableName);
        List<UserColComments> allTableColCommentsList = this.userColCommentsDao.selectUserColCommentsListByTbName(tableName, tableColumnLimitList);
        return allTableColCommentsList;
    }

    @Override
    public int getTableDataCount(String tableName, String tableConditionsJson, String customConditionsJson) {
        Map<String, UserColComments> allUserColCommentsMap = getAllUserColCommentsMapByTableName(tableName);
        List<UserColComments> colConditionList = convertTableConditionsJsonToList(allUserColCommentsMap, tableConditionsJson);
        List<CustomCondition> customConditionList = convertCustomConditionsJsonToList(allUserColCommentsMap, customConditionsJson);
        return this.userTablesDao.selectTableDataCountByConditions(tableName, colConditionList, customConditionList);
    }

}
