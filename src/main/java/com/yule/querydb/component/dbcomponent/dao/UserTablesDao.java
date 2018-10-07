package com.yule.querydb.component.dbcomponent.dao;

import com.yule.querydb.component.dbcomponent.entity.CustomCondition;
import com.yule.querydb.component.dbcomponent.entity.UserColComments;
import com.yule.querydb.component.dbcomponent.entity.UserTables;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yule
 * @date 2018/9/22 15:24
 */
public interface UserTablesDao {
    /**
     * 查询表名
     * @param tableName
     * @param canQueryTableNameList
     * @return
     */
    List<UserTables> selectUserTablesListByTbName(@Param("tableName") String tableName, @Param("canQueryTableNameList") List<String> canQueryTableNameList);

    /**
     * 根据表名+查询条件 查询数据
     * @param pageConfMap
     * @return
     */
    List<Map<String, String>> selectTableDataByConditions(Map<String, Object> pageConfMap);

    /**
     * 查询数量
     * @param tableName
     * @param colConditionList
     * @param customConditionList
     * @return
     */
    int selectTableDataCountByConditions(@Param("tableName") String tableName, @Param("colConditionList") List<UserColComments> colConditionList, @Param("customConditionList") List<CustomCondition> customConditionList);
}
