package com.yule.querydb.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.StringReader;
import java.sql.*;

/**
 * 自定义处理 JdbcType.LONGVARCHAR 和 java Object（String）
 * 处理转 mybatis 执行返回map的sql 报错，将 oracle Long 型转为 String 类型
 * @author yule
 * @date 2018/10/9 19:18
 */
@MappedTypes({Object.class})
@MappedJdbcTypes(value = {JdbcType.LONGVARCHAR})
public class MyObjectLongTypeHandle extends BaseTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        String parameterStr = (String) parameter;
        StringReader reader = new StringReader(parameterStr);
        ps.setCharacterStream(i, reader, parameterStr.length());
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        return str != null ? str : "";
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        return str != null ? str : "";
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        return str != null ? str : "";
    }
}
