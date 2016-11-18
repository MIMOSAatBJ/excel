package com.doumob.bean;

import com.doumob.util.DateUtil;

public class Pair implements Comparable<Pair> {
	public Record first;// 最早打卡记录
	public Record last;// 最晚打卡记录
	
	private Record vitial;//虚拟对象
	
	public Pair() {
	}
	public Pair(Integer empNo,String empName,String recordTime) {
		vitial=new Record();
		vitial.setEmpNo(empNo);
		vitial.setEmpName(empName);
		vitial.setRecordTime(recordTime);
	}

	public Record getFirst() {
		return first;
	}

	public void setFirst(Record first) {
		this.first = first;
	}

	public Record getLast() {
		return last;
	}

	public void setLast(Record last) {
		this.last = last;
	}
	
	

	public Record getVitial() {
		return vitial;
	}
	public void setVitial(Record vitial) {
		this.vitial = vitial;
	}
	/**
	 * @DESC:时间差，精确到分钟，任一数据为空，则返回0<br>
	 * @2015年9月7日<br>
	 * @autor:zhangH<br>
	 * @return
	 */
	public String getOurOff() {
		if (first == null || last == null) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(
					DateUtil.getOffMinute(first.getRecordTime(),
							last.getRecordTime()) / 60).append("小时,");
			sb.append(
					DateUtil.getOffMinute(first.getRecordTime(),
							last.getRecordTime()) % 60).append("分。");
			return sb.toString();
		}
	}

	/**
	 * @DESC:是否设置警告<br>
	 * @2015年9月8日<br>
	 * @autor:zhangH<br>
	 * @return
	 */
	public boolean isWarn() {
		if (first == null || last == null) {
			return DateUtil.isWorkDay(getRecordDay());
		} else {
			return DateUtil.getOffMinute(first.getRecordTime(),
					last.getRecordTime()) / 60 < 8;
		}
	}

	public String getEmpNo() {
		if(first!=null){
			return first.getEmpNo().toString();
		}else{
			return vitial.getEmpNo().toString();
		}
		
	}

	public String getEmpName() {
		if(first!=null){
			return first.getEmpName() == null ? "未知" : first.getEmpName();
		}
		else{
			return vitial.getEmpName() == null ? "未知" : vitial.getEmpName();
		}
	}
	
	public String getFirstTime(){
		if(first==null){
			return "";
		}else{
			return first.getRecordTime();
		}
	}

	public String getLastTime() {
		if (last == null) {
			return "";
		}
		return last.getRecordTime();
	}

	public String getDayOffWeek() {
		Integer day = DateUtil.getDayOfWeek(getRecordDay());
		String s="";
		switch (day) {
		case 1:
			s="星期日";
			break;
		case 2:
			s="星期一";
			break;
		case 3:
			s="星期二";
			break;
		case 4:
			s="星期三";
			break;
		case 5:
			s="星期四";
			break;
		case 6:
			s="星期五";
			break;
		case 7:
			s="星期六";
			break;
		default:
			s="";
			break;
		}
		return s;
	}

	/**
	 * 是否加班
	 *@2015年9月10日<br>
	 *@autor:zhangH<br>
	 *@DESC:<br>
	 *@return
	 */
	public boolean isAdd() {
		return !DateUtil.isWorkDay(getRecordDay())&&first!=null;
	}
	/**
	 * 周未为空
	 *@2015年9月10日<br>
	 *@autor:zhangH<br>
	 *@DESC:<br>
	 *@return
	 */
	public boolean isWeekend() {
		return !DateUtil.isWorkDay(getRecordDay())&&first==null;
	}
	
	public String[] toArry() {
		String[] s = { "0",/* getEmpNo(),*/ getEmpName(), getRecordDay(),
				getDayOffWeek(),
				getFirstTime(), getLastTime(), getOurOff() };
		return s;
	}

	/**
	 * @DESC:记录的实际天数<br>
	 * @2015年9月8日<br>
	 * @autor:zhangH<br>
	 * @return
	 */
	public String getRecordDay() {
		if(first!=null){
			return DateUtil.formatDate(DateUtil.getDate(first.getRecordTime()));
		}else {
			return DateUtil.formatDate(DateUtil.getDate(vitial.getRecordTime()));
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("员工编号:").append(first.getEmpNo()).append("==>");
		sb.append("员工姓名:").append(first.getEmpName()).append("==>");
		sb.append("打卡日期:").append(getRecordDay()).append("==>");
		if (first != null) {
			sb.append("first:").append(first.getRecordTime()).append("==>");
		}
		if (last != null) {
			sb.append("last:").append(last.getRecordTime()).append("==>");
		}
		sb.append("上班时长为:").append(getOurOff());
		return sb.toString();
	}

	public int compareTo(Pair o) {
		return vitial.getRecordTime().compareTo(o.getVitial().getRecordTime());
	}

}
