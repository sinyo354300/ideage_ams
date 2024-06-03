package com.ideage.ams.utils;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {

	//総レコード件数
	private int totalCount;
	//ページのレコード件数
	private int pageSize;
	//ページ数
	private int totalPage;
	//当ページ
	private int currPage;
	//リスト
	private List<?> list;

	/**
	 * ページ割り当て
	 * @param list
	 * @param totalCount
	 * @param pageSize
	 * @param currPage
	 */
	public PageResult(List<?> list, int totalCount, int pageSize, int currPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
}
