package com.carservice.project.business.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ResultTab for bootstrap-table
 * 
 * @author HC
 *
 */
public class ResultTab implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7856220417218810426L;
	
	// 当前�?
	private int pageNum;
	// 每页的数�?
	private int pageSize;
	// 总记录数
	private long total;
	// 结果�?
	private List<?> rows;
	//总页�?
	private int pageCount;

	private Map<String,String> totalRow;
	

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public ResultTab() {

	}

	public ResultTab(long total, List<?> rows) {
		this.total = total;
		this.rows = rows;
	}

	public ResultTab(long total, List<?> rows, int pageSize, int pageNum, int pageCount) {
		this.total = total;
		this.rows = rows;		
		this.pageSize=pageSize;
		this.pageNum=pageNum;
		this.pageCount=pageCount;
	}

	public Map<String, String> getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(Map<String, String> totalRow) {
		this.totalRow = totalRow;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}



}
