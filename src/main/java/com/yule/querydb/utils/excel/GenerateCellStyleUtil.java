package com.yule.querydb.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * 生成 HSSFCellStyle 样式  + 生成字体
 * @author yule
 * @date 2018/10/5 17:16
 */
public class GenerateCellStyleUtil {

    /**
     * 设置body的样式
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getBodyYellowStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
//        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

//        style2.setBorderBottom(BorderStyle.THIN);
//        style2.setBorderLeft(BorderStyle.THIN);
//        style2.setBorderRight(BorderStyle.THIN);
//        style2.setBorderTop(BorderStyle.THIN);

//        style2.setAlignment(HorizontalAlignment.CENTER);
//        style2.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFFont font2 = workbook.createFont();
//        font2.setBold(false);
        style2.setFont(font2);

        return style2;
    }

    /**
     * 标题的样式
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getHeaderBlueStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        //set the foreground fill color Note: Ensure Foreground color is set prior to background color.
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        //设置为一个填充单元格的前景颜色。
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置边框
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setBorderRight(BorderStyle.THIN);
//        style.setBorderTop(BorderStyle.THIN);
        //设置水平居中
//        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直居中
//        style.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        // 字体加粗
//        font.setBold(true);
        style.setFont(font);

        return style;
    }
}
