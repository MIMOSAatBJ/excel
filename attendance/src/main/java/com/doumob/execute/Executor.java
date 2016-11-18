package com.doumob.execute;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.doumob.bean.Pair;
import com.doumob.bean.Record;
import com.doumob.reader.ExcelReader;
import com.doumob.util.DateUtil;
import com.doumob.util.RecordUtil;
import com.doumob.writer.ExcelWriter;

public class Executor {
	private static Logger logger=Logger.getLogger(Executor.class);
	/**
	 *@2015-6-29<br>
	 *@autor:zhangH<br>
	 *@desc:执行解析，并入库<br>
	 *@param path 文件路径
	 *@throws Exception
	 */
	public static void execute(String path) throws Exception{
		logger.info("数据解析开始……");
		Long smillis = System.currentTimeMillis();
		ExcelReader reader=new ExcelReader();
		Workbook wb = reader.readWorkBook(path);
		Sheet sheet = reader.readSheet(wb, 0);
		List<Record> list=reader.getData(sheet);
		Map<Integer, List<Record>> map=RecordUtil.classify(list);
		Map<Integer, List<Pair>> result= RecordUtil.rmDuplicate(map);
		//写入
		ExcelWriter writer=new ExcelWriter();
		Workbook wwb=writer.createWorkBook();
		Sheet wsheet=wwb.createSheet();
		writer.writeHeader(wsheet);
		/*遍历查看*/
		for (Entry<Integer, List<Pair>> ent:result.entrySet()) {
			List<Pair> l=ent.getValue();
			writer.writeData(wsheet, l);
		}
		String file=DateUtil.getStandarDate()+"-考勤分析表.xls";
		writer.writeToFile(wwb, file);
		Long emillis=System.currentTimeMillis();
		logger.info("数据分析完毕，用时："+(emillis-smillis)/1000+"秒。");
		
	}
	
	public static void main(String[] args) throws Exception {
		File file=new File("");
		File dir=new File(file.getAbsolutePath()+"/");
		String path=file.getAbsolutePath()+"/";
		if (dir.isDirectory()) {
			File[] fs=dir.listFiles();
			for (int i = 0; i < fs.length; i++) {
				if (fs[i].isFile()&&(fs[i].getName().endsWith(".xls")||fs[i].getName().endsWith(".xlsx"))) {
					path=path+fs[i].getName();
				}
			}
		}
		//String path="C:/Users/killer/Desktop/kaoqin/8月.xls";
	    execute(path);
		Thread.sleep(3000);
		System.exit(0);
	}

}
