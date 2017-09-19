package jx_util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class poiTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		// 创建workbook
		Workbook wb = new HSSFWorkbook();
		// 创建sheet
		Sheet sheet = wb.createSheet();
		// 创建row
		Row row = sheet.createRow(3);
		// 创建cell
		Cell cell = row.createCell(3);

		// 设置内容
		cell.setCellValue("真的有这么吊吗");
		// 设置格式 内容
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 24);
		font.setFontName("华文彩云");

		// 创建格式
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);

		// 将cellStyle给cell
		cell.setCellStyle(cellStyle);

		// 保存（javase项目采用保存）
		FileOutputStream stream = new FileOutputStream(new File("f://a.xls"));
		wb.write(stream);// 将对象写进流
		
		stream.flush();
		stream.close();
		//8 下载(web项目才有下载)
		
		System.out.println("运行结束");

	}
}
