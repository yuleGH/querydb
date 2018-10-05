package com.yule.querydb.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yule
 * @date 2018/10/5 17:32
 */
public class ConvertCellValueUtil {

    public static void ConvertCellValue(HSSFCell cell, Object value) {
        if (value instanceof Integer) {
            Integer intValue = (Integer) value;
            cell.setCellValue(intValue.toString());
        } else if (value instanceof Float) {
            Float fValue = (Float) value;
            cell.setCellValue(fValue.toString());
        } else if (value instanceof Double) {
            Double dValue = (Double) value;
            cell.setCellValue(dValue.toString());
        } else if (value instanceof Long) {
            Long longValue = (Long) value;
            cell.setCellValue(longValue.toString());
        } else if (value instanceof Boolean) {
            Boolean bValue = (Boolean) value;
            cell.setCellValue(bValue);
        } else if (value instanceof Date)  {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = (Date) value;
            cell.setCellValue(sdf.format(date));
        }else {
            // 其它数据类型都当作字符串处理
            if(value != null){
                cell.setCellValue(value.toString());
            }else{
                cell.setCellValue("");
            }
        }
    }
}
