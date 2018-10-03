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
     * 查询某个表下的所有字段
     * @param tableName
     * @return
     */
    List<UserColComments> selectUserColCommentsListByTbName(@Param("tableName") String tableName);

    /**
     * 根据列名查询
     * @param tableName
     * @param colConditionList
     * @return
     */
    List<UserColComments> selectUserColCommentsListByColumns(@Param("tableName") String tableName, @Param("colConditionList") List<UserColComments> colConditionList);
}
