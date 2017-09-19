package cn.ajajaaj.action.cargo;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;

import cn.ajajaaj.action.BaseAction;
import cn.ajajaaj.jx.domain.ContractProduct;
import cn.ajajaaj.jx.service.ContractProductService;
import cn.ajajaaj.utils.DownloadUtil;
import cn.ajajaaj.utils.UtilFuns;

public class OutProductAction extends BaseAction {
private String inputDate;
	
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String toedit() throws Exception {
		return"toedit";
	}
	
	private ContractProductService contractProductService;
	
	public void setContractProductService(
			ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}
	@SuppressWarnings("resource")
	public String print() throws Exception {
		//创建工作表
		Workbook wb = new HSSFWorkbook();
		//创建工作表
		Sheet sheet = wb.createSheet();
		
		//设置列宽
		sheet.setColumnWidth(0, 6*256);
		sheet.setColumnWidth(1, 26*256);
		sheet.setColumnWidth(2, 12*256);
		sheet.setColumnWidth(3, 30*256);
		sheet.setColumnWidth(4, 12*256);
		sheet.setColumnWidth(5, 15*256);
		sheet.setColumnWidth(6, 10*256);
		sheet.setColumnWidth(7, 10*256);
		sheet.setColumnWidth(8, 10*256);
		//定义一些公共变量
		//行对象
		Row nRow = null;
		//单元格对象
		Cell nCell = null;
		
		//行号和列号
		int rowNo = 0;
		int cellNo = 1;
		
		/************大标题的打印**************/
		nRow = sheet.createRow(rowNo);
		nCell = nRow.createCell(cellNo);
		//横向合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
		
		//设置内容：
		//设置内容：
		/**
		 * 2012-01:
		 * 2012-10:
		 * 
		 * 方式一：inputDate.replace("-0","-").replace("-","年")
		 * 方式二：inputDate.replace("-0","年").replace("-","年")
		 * 
		 */
		nCell.setCellValue(inputDate.replace("-0","-").replace("-","年")+"月出货表");
		
		//行高？样式？
		nRow.setHeightInPoints(36f);
		nCell.setCellStyle(bigTitle(wb));
		
		/************小标题的打印**************/
		//得先换行
		rowNo++;
		//创建行对象
		nRow = sheet.createRow(rowNo);
		
		String[] titles = {"客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
		//遍历标题，进行输出打印
		for(String title:titles){
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(title);
			nCell.setCellStyle(title(wb));
		}
		
		/************内容的打印**************/
		//准备数据
		//String hql = "from ContractProduct where contract.shipTime like '%"+inputDate+"%'";//mysql支持，oracle不支持
		//hql中有to_char函数吗？没有，这是oracle中的pl/sql函数
		//但是，hibernate强大的地方就在：你可以在HQL中使用数据库中的函数
		String hql = "from ContractProduct where to_char(contract.shipTime,'yyyy-mm') = '"+inputDate+"'";//oracle支持
		//查询:
		List<ContractProduct>list = contractProductService.find(hql, ContractProduct.class, null);
		
		//将数据放入sheet中
		for(ContractProduct cp:list){
			//行变化
			rowNo++;
			nRow = sheet.createRow(rowNo);
			//列
			cellNo = 1;//规1
			
			//"客户",
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getCustomName());
			nCell.setCellStyle(text(wb));
			
			//"订单号",
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getContractNo());
			nCell.setCellStyle(text(wb));
			//"货号",
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getProductNo());
			nCell.setCellStyle(text(wb));
			//"数量",
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getCnumber());
			nCell.setCellStyle(text(wb));
			//"工厂",
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getFactoryName());
			nCell.setCellStyle(text(wb));
			//"工厂交期",
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));
			nCell.setCellStyle(text(wb));
			//"船期",
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
			nCell.setCellStyle(text(wb));
			//"贸易条款"
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getTradeTerms());
			nCell.setCellStyle(text(wb));
			
		}
		
		
		/************下载**************/
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		//将内容写入流
		wb.write(byteArrayOutputStream);
		//使用流
		DownloadUtil downloadUtil = new DownloadUtil();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		/**
		 * 第一个：文件流
		 * 第二个：response
		 * 第三个：下载的文件的名字
		 */
		downloadUtil.download(byteArrayOutputStream, response, "itcast.xls");
		
		return NONE;
	}
	//大标题的样式
	public CellStyle bigTitle(Workbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)16);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);					//字体加粗
		
		style.setFont(font);
		
		style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		return style;
	}
	//小标题的样式
	public CellStyle title(Workbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short)12);
		
		style.setFont(font);
		
		style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
		style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
		style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
		style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
		
		return style;
	}
	
	//文字样式
	public CellStyle text(Workbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short)10);
		
		style.setFont(font);
		
		style.setAlignment(CellStyle.ALIGN_LEFT);					//横向居左
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
		style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
		style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
		style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
		
		return style;
	}
}
