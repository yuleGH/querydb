package com.yule.querydb.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.StringReader;
import java.sql.*;

/**
 * 自定义处理 JdbcType.Blob 和 java Object（String）
 * 处理转 json 报错，将 oracle.sql.Blob 类型转为 String 类型
 * copy org.apache.ibatis.type.BlobTypeHandler 类的处理
 * @author yule
 * @date 2018/10/9 17:57
 */
@MappedTypes({Object.class})
@MappedJdbcTypes(value = {JdbcType.BLOB, JdbcType.LONGVARCHAR})
public class MyObjectBlobTypeHandle extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        byte[] parameterByteArray = (byte[]) parameter;
        String parameterStr = new String(parameterByteArray);
        StringReader reader = new StringReader(parameterStr);
        ps.setCharacterStream(i, reader, parameterStr.length());
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Blob blob = rs.getBlob(columnName);
        byte[] returnValue = null;
        if(null != blob) {
            returnValue = blob.getBytes(1L, (int)blob.length());
        }

        return new String(returnValue);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Blob blob = rs.getBlob(columnIndex);
        byte[] returnValue = null;
        if(null != blob) {
            returnValue = blob.getBytes(1L, (int)blob.length());
        }

        return new String(returnValue);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Blob blob = cs.getBlob(columnIndex);
        byte[] returnValue = null;
        if(null != blob) {
            returnValue = blob.getBytes(1L, (int)blob.length());
        }

        return new String(returnValue);
    }
}
