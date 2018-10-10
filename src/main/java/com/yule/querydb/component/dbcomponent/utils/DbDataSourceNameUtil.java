package com.yule.querydb.component.dbcomponent.utils;

import com.yule.querydb.utils.CommonUtil;
import com.yule.querydb.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据源名字查询
 *
 * @author yule
 * @date 2018/10/10 16:08
 */
public class DbDataSourceNameUtil {
    private static Map<String, String> dataSourceNamesMap = buildDataSourceNamesMap();

    private static final Logger logger = LoggerFactory.getLogger(DbDataSourceNameUtil.class);

    /**
     * 初始化  dataSourceNamesMap
     * @return
     */
    private static Map<String, String> buildDataSourceNamesMap() {
        Map<String, String> dataSourceNamesMap = new HashMap<>(4);

        String dbComponentDataSources = PropertiesUtils.getValue("dbComponentDataSources");
        String dbComponentDataSourceNames = PropertiesUtils.getValue("dbComponentDataSourceNames");
        if(CommonUtil.isEmpty(dbComponentDataSources)){
            logger.error("缺少配置 dbComponentDataSources！请配置后重试。");
        }
        if(CommonUtil.isEmpty(dbComponentDataSourceNames)){
            logger.error("缺少配置 dbComponentDataSourceNames！请配置后重试。");
        }

        String[] dbComponentDataSourceArray = dbComponentDataSources.split(",");
        String[] dbComponentDataSourceNameArray = dbComponentDataSourceNames.split(",");
        for(int i = 0; i < dbComponentDataSourceArray.length; i++){
            dataSourceNamesMap.put(dbComponentDataSourceArray[i].trim(), dbComponentDataSourceNameArray[i].trim());
        }
        return dataSourceNamesMap;
    }

    /**
     * 获取数据源名字的map
     * @return
     */
    public static Map<String, String> getDbDataSourceNamesMap(){
        return dataSourceNamesMap;
    }
}
