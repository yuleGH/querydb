package com.yule.querydb.utils.excel;

import com.yule.querydb.utils.CommonUtil;
import org.apache.poi.hssf.usermodel.*;

import java.util.Arrays;
import java.util.List;

/**
 * 生成excel标题
 * @author yule
 * @date 2018/10/5 17:19
 */
public class GenerateHeaderUtil {

    /**
     * 设置标题，从 0 行开始，宽度 5000
     * @param headers
     * @param sheet
     * @param style
     */
    public static void setHeader(String[] headers, HSSFSheet sheet, HSSFCellStyle style) {
        setHeader(Arrays.asList(headers), sheet, style, 0, 5000);
    }

    /**
     * 设置标题，从 rowNum 行开始，宽度 5000
     * @param headers
     * @param sheet
     * @param style
     * @param rowNum
     */
    public static void setHeader(String[] headers, HSSFSheet sheet, HSSFCellStyle style, int rowNum) {
        setHeader(Arrays.asList(headers), sheet, style, rowNum, 5000);
    }

    /**
     * 设置标题，从0行开始，宽度 5000
     * @param headerList
     * @param sheet
     * @param style
     */
    public static void setHeader(List<String> headerList, HSSFSheet sheet, HSSFCellStyle style) {
        setHeader(headerList, sheet, style, 0, 5000);
    }

    /**
     * 设置自动宽度，从 0 行开始
     * @param headerList
     * @param sheet
     * @param style
     */
    public static void setHeaderForAutoWidth(List<String> headerList, HSSFSheet sheet, HSSFCellStyle style) {
        setHeader(headerList, sheet, style, 0, null);
    }

    /**
     * 设置自动宽度，从 rowNum 行开始
     * @param headerList
     * @param sheet
     * @param style
     * @param rowNum
     */
    public static void setHeaderForAutoWidth(List<String> headerList, HSSFSheet sheet, HSSFCellStyle style, int rowNum) {
        setHeader(headerList, sheet, style, rowNum, null);
    }

    /**
     * 设置标题
     * @param headerList
     * @param sheet
     * @param style
     * @param rowNum
     * @param columnWidth
     */
    private static void setHeader(List<String> headerList, HSSFSheet sheet, HSSFCellStyle style, int rowNum, Integer columnWidth) {
        HSSFRow row = sheet.createRow(rowNum);
        row.setHeight((short) 300);
        row.setHeightInPoints(20);
        HSSFCell cell;
        if(CommonUtil.isNotNullOrBlock(headerList)){
            for (int i = 0; i < headerList.size(); i++) {
                HSSFRichTextString text = new HSSFRichTextString(headerList.get(i));
                if(null == columnWidth){
                    //设置列宽
                    sheet.setColumnWidth((short) i, (short) headerList.get(i).getBytes().length*2*256);
                }else{
                    //设置列宽
                    sheet.setColumnWidth((short) i, columnWidth.shortValue());
                }
                cell = row.createCell(i);
                cell.setCellStyle(style);
                cell.setCellValue(text);
            }
        }
    }

}
