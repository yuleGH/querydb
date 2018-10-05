package com.yule.querydb.utils.excel;

import com.yule.querydb.utils.CommonUtil;
import com.yule.querydb.utils.ReflectUtil;
import org.apache.poi.hssf.usermodel.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author yule
 * @date 2018/10/5 17:23
 */
public class GenerateBodyDataUtil {
    /**
     * 设置表格体 数据，从 1 行开始
     * @param datas
     * @param sheet
     * @param style2
     * @param fields
     */
    public static void setBodyData(List<?> datas, HSSFSheet sheet, HSSFCellStyle style2, String[] fields) {
        setBodyData(datas, sheet, style2, Arrays.asList(fields), 1);
    }

    /**
     * 设置表格体 数据，从 1 行开始
     * @param datas
     * @param sheet
     * @param style2
     * @param fieldList
     */
    public static void setBodyData(List<?> datas, HSSFSheet sheet, HSSFCellStyle style2, List<String> fieldList) {
        setBodyData(datas, sheet, style2, fieldList, 1);
    }

    /**
     * 设置表格数据，从 headerRowNum 行开始
     * @param datas
     * @param sheet
     * @param style2
     * @param fieldList
     * @param headerRowNum
     */
    public static void setBodyData(List<?> datas, HSSFSheet sheet, HSSFCellStyle style2, List<String> fieldList, int headerRowNum) {
        HSSFCell cell;
        HSSFRow row;
        // 遍历集合数据，产生数据行
        // 利用反射，根据javabean属的先后顺序，动调用getXxx()方法得到属
        if(datas != null && datas.size()>0) {
            Object value;
            for (short i = 0; i < datas.size(); i++) {
                Object t = datas.get(i);
                row = sheet.createRow(i + headerRowNum);
                if (CommonUtil.isNotNullOrBlock(fieldList)) {
                    for (int f = 0; f < fieldList.size(); f++) {
                        cell = row.getCell(f);
                        if(cell != null){
                            break;
                        }
                        cell = row.createCell(f);
                        cell.setCellStyle(style2);
                        String fieldName = fieldList.get(f);

                        if (t instanceof Map) {
                            Map<String, Object> map = (Map<String, Object>) t;
                            value = map.get(fieldName);
                        } else {
                            value = ReflectUtil.getFieldValue(t, fieldName);
                        }
                        // 判断值的类型后进行强制类型转
                        ConvertCellValueUtil.ConvertCellValue(cell, value);
                    }
                }
            }
        }
    }
}
