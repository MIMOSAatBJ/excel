package com.doumob.bean;

import java.io.Serializable;

public class Record implements Comparable<Record>, Serializable {
	private static final long serialVersionUID = 1L;
	private Integer empNo;// 工号
	private String empName;// 姓名
	private String recordTime;// 打卡时间

	// private Integer number;
	// private String place;
	// private String type;
	// private String demp;
	public Integer getEmpNo() {
		return empNo;
	}

	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public int compareTo(Record o) {
		return getRecordTime().compareTo(o.getRecordTime());
	}
	
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("empNo:").append(empNo).append(",");
		sb.append("empName:").append(empName).append(",");
		sb.append("recordTime:").append(recordTime);
		return sb.toString();
	}

}
