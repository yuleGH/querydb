package com.yule.querydb.datasource;

/**
 * 数据源持有类
 * @author yule
 * @date 2018/9/28 19:34
 */
public class DataSourceHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /**
     * @Description: 设置数据源类型
     * @param dataSourceType  数据库类型
     * @return void
     * @throws
     */
    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    /**
     * @Description: 获取数据源类型
     * @return String
     * @throws
     */
    public static String getDataSourceType() {
        return contextHolder.get();
    }

    /**
     * @Description: 清除数据源类型
     * @return void
     * @throws
     */
    public static void clearDataSourceType() {
        contextHolder.remove();
    }

}
