package com.cqut.genhoo;

import java.util.List;

/**
* 项目名称：DD
* 类名称：PageObject
* 类描述：封装分页的对象   
* 创建人：胡均
* 创建时间：2014-1-6 下午4:37:56
* 修改人：胡均
* 修改时间：2014-1-6 下午4:37:56
* 修改备注：   
* @version 1.0
* Copyright (c) 2014 ChongQing University Of Technology
 */
public class Page<T> {
	
	//每页显示的数量  默认页面的大小为 10
	private int pageSize = SysConstant.DEFAULT_LIMT_SIZE;
	
	//总共数量
	private int total;
	
	//当期是第几页
	private int index;
	
	private List<T> dataList;
	
	@SuppressWarnings("unused")
	private String pageHtml;
	
	//用于分页的下面的按钮URL
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	/**
	* 创建分页的链接
	* @Title: createPageHtml 
	* @Description:  
	* @return 
	* @return String
	 */
	public String getPageHtml() {
		if(url==null){
			return "";
		}else{
			return SysUtil.createPage(total, index, pageSize, url);
		}
	}

	public void setPageHtml(String pageHtml) {
		this.pageHtml = pageHtml;
	}
	
}
