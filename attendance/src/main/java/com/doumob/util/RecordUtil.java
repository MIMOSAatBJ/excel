package com.doumob.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;

import com.doumob.bean.Pair;
import com.doumob.bean.Record;

public class RecordUtil {

	/**
	 * 
	 * @DESC:从excel某一行中，读取需要的bean，在此方法中，只选择了需要读取的下标<br>
	 * @2015年9月7日<br>
	 * @autor:zhangH<br>
	 * @param row
	 * @return
	 */
	public static Record readLine(Row row) {
		Record r = null;
		if (row != null) {
			r = new Record();
			Double number = row.getCell(0).getNumericCellValue();// 第1个，为编号
			String name = row.getCell(1) == null ? "" : row.getCell(1)
					.getStringCellValue();// 第2个，为姓名
			Date date = row.getCell(3).getDateCellValue();// 第四个，为打日期
			r.setEmpNo(number.intValue());
			r.setEmpName(name);
			r.setRecordTime(DateUtil.formatDate(DateUtil.long_patten, date));
		}
		return r;
	}

	/**
	 *@DESC:对数据进行分组，同一人的数据放入同一list,<br>
	 *@2015年9月7日<br>
	 *@autor:zhangH<br>
	 *@param recordList
	 *@return
	 */
	public static Map<Integer, List<Record>> classify(List<Record> recordList) {
		Map<Integer, List<Record>> map = new HashMap<Integer, List<Record>>();
		for (Record r : recordList) {
			List<Record> list=map.get(r.getEmpNo());
			if(list==null){
				list=new ArrayList<Record>();
				list.add(r);
				map.put(r.getEmpNo(), list);
			}else{
				list.add(r);
			}
		}
		/*排序
		 */
		for (Entry<Integer, List<Record>> person:map.entrySet()) {
			List<Record> records=person.getValue();
			Collections.sort(records);
		}
		return map;
	}
	
	/**
	 *@DESC:同一天多次打卡，只记录最早一次和最后一次<br>
	 *@2015年9月7日<br>
	 *@autor:zhangH<br>
	 *@param list
	 *@return
	 */
	public static Map<Integer, List<Pair>> rmDuplicate(Map<Integer, List<Record>> map){
		Map<Integer, List<Pair>> result=new HashMap<Integer, List<Pair>>();
		for (Entry<Integer, List<Record>> ent:map.entrySet()) {
			 Integer key=ent.getKey();
			 List<Pair> lp=computePair(ent.getValue());
			 Collections.sort(lp);
			 result.put(key,lp);
		}
		return result;
	}
	
	public static List<Pair> computePair(List<Record> list){
		Map<String,Pair> map=initMap(list.get(0));
		for (Record r:list) {
		   String ymd=DateUtil.formatDate(DateUtil.getDate(r.getRecordTime()));
		   Pair lp=map.get(ymd);
		   if(lp.getFirst()==null){
			   lp.setFirst(r);
		   }else{
			   lp.setLast(r);
		   }
		}
		return new ArrayList<Pair>(map.values());
	}
	
	public static Map<String,Pair> initMap(Record r){
		 Map<String,Pair> map=new HashMap<String, Pair>();
		 String ymd=DateUtil.formatDate(DateUtil.getDate(r.getRecordTime()));
		 int day=DateUtil.getActulDays(ymd);
		 String yyyy_mm=DateUtil.formatDate("yyyy-MM", DateUtil.getDate(ymd));
		 for (int i = 0; i <day; i++) {
			if(i<9){
				String rd=yyyy_mm+"-"+"0"+(i+1);
				map.put(rd, new Pair(r.getEmpNo(),r.getEmpName(),rd));
			}else{
				String rd=yyyy_mm+"-"+(i+1);
				map.put(rd,new Pair(r.getEmpNo(),r.getEmpName(),rd));
			}
		 }
		 return map;
	}
}
