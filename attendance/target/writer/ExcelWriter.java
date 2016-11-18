package com.doumob.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.doumob.bean.Pair;

public class ExcelWriter {

//	private String[] header = { "序号", "员工编号", "姓名", "日期", "首次打卡", "最后打卡",
//			"时长","星期","是否加班" };
	private String[] header = { "序号", "姓名", "日期","星期", "首次打卡", "最后打卡",
			"时长","是否加班" };

	/**
	 * 创建工作薄
	 *
	 * @2015年9月8日<br>
	 * @autor:zhangH<br>
	 * @DESC:<br>
	 * @return
	 */
	public Workbook createWorkBook() {
		return new XSSFWorkbook();
	}

	/**
	 * 创建sheet
	 *
	 * @2015年9月8日<br>
	 * @autor:zhangH<br>
	 * @DESC:<br>
	 * @param wb
	 * @return
	 */
	public Sheet createSheet(Workbook wb) {
		return wb.createSheet();
	}

	public Sheet writeHeader(Sheet sheet) {
		Row row = sheet.createRow(0);
		for (int i = 0; i < header.length; i++) {
			Cell cell=row.createCell(i);
			cell.setCellValue(header[i]);
			cell.setCellStyle(createStyle(sheet));
			sheet.setColumnWidth(i, 4000);
		}
		return sheet;
	};

	public void writeData(Sheet sheet, List<Pair> data) {
		int index=sheet.getLastRowNum()+1;
		for (int i = 0; i < data.size(); i++) {
			index++;
			Pair p=data.get(i);
			Row row = sheet.createRow(index);
			String[] s=p.toArry();
			for (int j = 0; j < s.length; j++) {
				String cv=s[j];
				if(j==0){
					cv=String.valueOf(i+1);
				}
				Cell cell=row.createCell(j);
				cell.setCellValue(cv);
				if(p.isWarn()){
					cell.setCellStyle(warnStyle(sheet));
				}else if(p.isWeekend()){
					cell.setCellStyle(weekEndStyle(sheet));
				}
				else{
					cell.setCellStyle(createStyle(sheet));
				}
			}
			if(p.isAdd()){
				Cell cell=row.createCell(s.length);
				cell.setCellValue("是");
				cell.setCellStyle(createStyle(sheet));
			}
		}
		
	}

	/**
	 * @DESC:写入到文件<br>
	 * @2015年9月8日<br>
	 * @autor:zhangH<br>
	 * @param wb
	 * @param path
	 */
	public void writeToFile(Workbook wb, String path) {
		try {
			FileOutputStream stream = new FileOutputStream(new File(path));
			wb.write(stream);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建特殊定制的样式
	 * 
	 * @param sheet
	 * @return
	 */
	public CellStyle createStyle(Sheet sheet) {
		CellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font font = sheet.getWorkbook().createFont();
		font.setColor(HSSFColor.BLACK.index);
		style.setWrapText(true);
		style.setFont(font);
		return style;
	}
	
	public CellStyle warnStyle(Sheet sheet) {
		CellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font font = sheet.getWorkbook().createFont();
		font.setColor(HSSFColor.RED.index);
		style.setWrapText(true);
		style.setFont(font);
		return style;
	}
	public CellStyle addStyle(Sheet sheet) {
		CellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font font = sheet.getWorkbook().createFont();
		font.setColor(HSSFColor.GREEN.index);
		style.setWrapText(true);
		style.setFont(font);
		return style;
	}
	public CellStyle weekEndStyle(Sheet sheet) {
		CellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font font = sheet.getWorkbook().createFont();
		font.setColor(HSSFColor.AQUA.index);
		style.setWrapText(true);
		style.setFont(font);
		return style;
	}
}
