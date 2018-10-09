package com.yule.querydb.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 生成字体
 * @author yule
 * @date 2018/10/5 17:25
 */
public class GenerateFontUtil {

    /**
     * 红色 + 三振横线
     * @param workbook
     * @return
     */
    public static HSSFFont getRedAndStrikeoutFont(HSSFWorkbook workbook) {
        HSSFFont redAndStrikeoutFont = getRedFont(workbook);
        //设置是否在文本中使用三振横线
        redAndStrikeoutFont.setStrikeout(true);
        return redAndStrikeoutFont;
    }

    /**
     * 红字 + 加粗
     * @param workbook
     * @return
     */
    public static HSSFFont getRedAndBoldFont(HSSFWorkbook workbook) {
        HSSFFont redAndBoldFont = getRedFont(workbook);
//        redAndBoldFont.setBold(true);
        return redAndBoldFont;
    }

    /**
     * 红色
     * @param workbook
     * @return
     */
    public static HSSFFont getRedFont(HSSFWorkbook workbook) {
        HSSFFont redFont = getFont(workbook);
        redFont.setColor(HSSFColor.RED.index);
        return redFont;
    }

    /**
     * 绿色
     * @param workbook
     * @return
     */
    public static HSSFFont getGreenFont(HSSFWorkbook workbook) {
        HSSFFont greenFont = getFont(workbook);
        greenFont.setColor(HSSFColor.GREEN.index);
        return greenFont;
    }

    /**
     * 蓝色
     * @param workbook
     * @return
     */
    public static HSSFFont getBlueFont(HSSFWorkbook workbook) {
        HSSFFont blueFont = getFont(workbook);
        blueFont.setColor(HSSFColor.BLUE.index);
        return blueFont;
    }

    /**
     * 最普通的字体
     * @param workbook
     * @return
     */
    public static HSSFFont getCommonFont(HSSFWorkbook workbook){
        return getFont(workbook);
    }

    private static HSSFFont getFont(HSSFWorkbook workbook){
        HSSFFont font2 = workbook.createFont();
//        font2.setBold(false);
        return font2;
    }
}
