package com.yule.querydb.utils.excel;

import com.yule.querydb.utils.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 *  使用 poi 导出 excel 工具类
 *  poi api 的官网：http://poi.apache.org/apidocs/
 *  现在支持版本 3.17
 * @author yule
 * @date 2018/10/5 16:48
 */
public class ExportExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExportExcelUtil.class);

	/**
	 * 导出 excel 文件
	 * @param request
	 * @param response
	 * @param fileName
	 * @param headers
	 * @param fields
	 * @param datas
	 * @throws Exception
	 */
	public static void exportExcel(HttpServletRequest request, HttpServletResponse response,
								   String fileName, String[] headers, String[] fields, List<?> datas) throws Exception {
		exportExcel(request, response, fileName, Arrays.asList(headers), Arrays.asList(fields), datas );
	}

	/**
	 * 导出 excel 文件
	 * @param request
	 * @param response
	 * @param fileName
	 * @param headerList
	 * @param fieldList
	 * @param datas
	 * @throws Exception
	 */
	public static void exportExcel(HttpServletRequest request, HttpServletResponse response,
								   String fileName, List<String> headerList, List<String> fieldList, List<?> datas) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		OutputStream out = response.getOutputStream();
		try {
			fileName += "_" + DateUtil.getNow(DateUtil.FMT_YYYYMMDD);
			HSSFSheet sheet = workbook.createSheet(fileName);

			//1:style 生成样式  + 生成字体
			HSSFCellStyle style = GenerateCellStyleUtil.getHeaderBlueStyle(workbook);

			//2:stypl2 生成并设置另样式  + 生成另一个字
			HSSFCellStyle style2 = GenerateCellStyleUtil.getBodyYellowStyle(workbook);

			// 产生表格标题，自适应宽度
			GenerateHeaderUtil.setHeaderForAutoWidth(headerList, sheet, style);

			//设置内容
			GenerateBodyDataUtil.setBodyData(datas, sheet, style2, fieldList);

			response.setContentType("application/x-msdownload;charset=utf8");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "iso-8859-1") + ".xls");
			workbook.write(out);
		} catch (Exception e) {
			logger.error("POI 导出Excel文件 出错", e);
			throw e;
		} finally {
			out.flush();
			out.close();
		}
	}

}
