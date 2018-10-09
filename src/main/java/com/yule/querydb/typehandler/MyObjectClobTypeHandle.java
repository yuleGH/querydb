package com.yule.querydb.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.StringReader;
import java.sql.*;

/**
 * 自定义处理 JdbcType.Blob 和 java Object（String）
 * 处理转 json 报错，将 oracle.sql.Clob 类型转为 String 类型
 * copy org.apache.ibatis.type.ClobTypeHandler 类的处理
 * @author yule
 * @date 2018/10/9 17:57
 */
@MappedTypes({Object.class})
@MappedJdbcTypes(value = {JdbcType.CLOB})
public class MyObjectClobTypeHandle extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        String parameterStr = (String) parameter;
        StringReader reader = new StringReader(parameterStr);
        ps.setCharacterStream(i, reader, parameterStr.length());
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = "";
        Clob clob = rs.getClob(columnName);
        if(clob != null) {
            int size = (int)clob.length();
            value = clob.getSubString(1L, size);
        }

        return value;
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = "";
        Clob clob = rs.getClob(columnIndex);
        if(clob != null) {
            int size = (int)clob.length();
            value = clob.getSubString(1L, size);
        }

        return value;
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = "";
        Clob clob = cs.getClob(columnIndex);
        if(clob != null) {
            int size = (int)clob.length();
            value = clob.getSubString(1L, size);
        }

        return value;
    }

}
