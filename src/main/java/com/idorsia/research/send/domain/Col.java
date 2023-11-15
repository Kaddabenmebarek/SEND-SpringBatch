package com.idorsia.research.send.domain;

import java.util.LinkedList;

public class Col {

	private String header;
	private LinkedList<String> rows;
	private Integer colNum;

	public Col() {
		super();
	}

	public Col(String header, LinkedList<String> rows, Integer colNum) {
		super();
		this.header = header;
		this.rows = rows;
		this.colNum = colNum;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public LinkedList<String> getRows() {
		return rows;
	}

	public void setRows(LinkedList<String> rows) {
		this.rows = rows;
	}

	public Integer getColNum() {
		return colNum;
	}

	public void setColNum(Integer colNum) {
		this.colNum = colNum;
	}

}
