package com.doumob.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.doumob.bean.Record;
import com.doumob.util.RecordUtil;

/**
 * excel读取,excel有.xls和.xlsx两种不同的文件
 * 
 * @author killer
 * 
 */
public class ExcelReader {
	private Logger logger = Logger.getLogger(getClass());
	public String suffix_xls = ".xls";
	public String suffix_xlsx = ".xlsx";

	public Workbook readWorkBook(String path) throws Exception {
		Workbook hw = WorkbookFactory.create(new File(path));  
		return hw;
	}

	/**
	 * @2015-6-27<br>
	 * @autor:zhangH<br>
	 * @desc:从指定的workbook中读取sheet表单<br>
	 * @param wb
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public Sheet readSheet(Workbook wb, Integer index) throws Exception {
		return wb.getSheetAt(index);
	};

	/**
	 * 
	 * @2015-6-26<br>
	 * @autor:zhangH<br>
	 * @desc:读取sheet中指定下标的行<br>
	 * @param sheet
	 *            需要读取的sheet
	 * @param index
	 *            下标
	 * @return
	 */
	public Row readRow(Sheet sheet, Integer index) {
		logger.info("当前读取第：" + index + "行。");
		return sheet.getRow(index);
	}

	/**
	 * @2015-6-26<br>
	 * @autor:zhangH<br>
	 * @desc:读取cell数据，index为指定的下标<br>
	 * @param row
	 *            cell所在行
	 * @param index
	 *            其下标
	 * @return
	 */
	public Cell readCell(Row row, Integer index) {
		return row.getCell(index);
	}

	/**
	 * 
	 * @2015-6-27<br>
	 * @autor:zhangH<br>
	 * @desc:读取一行<br>
	 * @param row
	 *            要读取的行
	 * @param start
	 *            开始读取位置
	 * @param end
	 *            结束位置
	 * @return 该行所有数据的String形式
	 */
	public String[] readLine(Row row, Integer start, Integer end) {
		String[] line = new String[end - start];
		for (int i = 0; i < line.length; i++) {
			line[i] = row.getCell(i + start).toString();
		}
		return line;
	}

	/**
	 * @2015-6-29<br>
	 * @autor:zhangH<br>
	 * @desc:从sheet表单中，得到当前sheet表的表头<br>
	 * @param sheet
	 * @param index
	 *            header所在的行标
	 * @param start
	 *            header开始列
	 * @param end
	 *            header结束列
	 * @return
	 */
	public String[] getHeader(Sheet sheet, Integer index, Integer start,
			Integer end) {
		Row row = sheet.getRow(index);
		return readLine(row, start, end);
	}

	public List<Record> getData(Sheet sheet){
		List<Record> list=new ArrayList<Record>();
		for (int i = 1; i < sheet.getLastRowNum(); i++) {
			Row row = readRow(sheet, i);
			Record r=RecordUtil.readLine(row);
			list.add(r);
		}
		return list;
	}

}
