package com.yule.querydb.component.dbcomponent.dao;

import com.yule.querydb.component.dbcomponent.entity.UserColComments;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author yule
 * @date 2018/9/22 15:37
 */
public interface UserColCommentsDao {
    /**
     * 查询某个表下的所有字段，有限制
     * @param tableName
     * @param tableColumnLimitList 字段限制
     * @return
     */
    List<UserColComments> selectUserColCommentsListByTbName(@Param("tableName") String tableName, @Param("tableColumnLimitList") List<String> tableColumnLimitList);

    /**
     * 根据列名查询，有限制
     * @param tableName
     * @param tableColumnLimitList 字段限制
     * @param colConditionList
     * @return
     */
    List<UserColComments> selectUserColCommentsListByColumns(@Param("tableName") String tableName, @Param("tableColumnLimitList") List<String> tableColumnLimitList, @Param("colConditionList") List<UserColComments> colConditionList);
}
