package com.aoe.umbrella.entity;

import java.util.List;

public class Page<T> {
	protected int pageIndex;
	protected int pageSize;	
	protected int totalPage;
	protected int totalCount;
	protected String filter;
	protected String orderBy;
	protected List<T> results;

	public Page(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}
	
	public Page(List<T> results) {
		this.results = results ;
	}
	
	public Page(int pageIndex, int pageSize,int totalCount,int totalPage,String filter,String orderBy) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalPage=totalPage;
		this.filter=filter;
		this.totalCount=totalCount;
		this.orderBy=orderBy;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if (totalCount < 0) {
			totalPage = 0;
		} else {
			totalPage = (totalCount / pageSize) + (totalCount % pageSize == 0 ? 0 : 1);
		}
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public String getFilter() {
		return filter;
	}

	public String getOrderBy() {
		return orderBy;
	}
	
}