package com.task04.statistic.vo;

public class StatisticVO {
	private String type;
	private int value;
	private String date;
	
	public StatisticVO() {}

	public StatisticVO(String type, int value, String date) {
		super();
		this.type = type;
		this.value = value;
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "StatisticVO [type=" + type + ", value=" + value + ", date=" + date + "]";
	}
	
}
