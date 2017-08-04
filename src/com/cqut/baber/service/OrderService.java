package com.cqut.baber.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import com.cqut.baber.dao.OrderDAO;
import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseService;

public class OrderService extends BaseService {
	private OrderDAO dao = new OrderDAO();
	
	//admin
	public Page<Map<String,Object>> queryOnePageCheckOrders(int pageIndex, int pageSize,String orderStatus) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.queryOnePageCheckOrders(pageIndex, pageSize,orderStatus);
	}	
	
	public Page<Map<String,Object>> queryOnePageApplyDrawbackOrders(int pageIndex, int pageSize,String orderStatus) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.queryOnePageApplyDrawbackOrders(pageIndex, pageSize,orderStatus);
	}	
	
	//baber
	public Page<Map<String,Object>> queryOnePagePreOrders(int pageIndex, int pageSize,long loginBaberId) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.queryOnePagePreOrders(pageIndex, pageSize,loginBaberId);
	}	 
	
	public Page<Map<String,Object>> queryOnePageOrders(int pageIndex, int pageSize,long loginBaberId) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.queryOnePageOrders(pageIndex, pageSize,loginBaberId);
	}	 
	
	public Page<Map<String,Object>> queryOnePageFinishOrders(int pageIndex, int pageSize,long loginBaberId) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.queryOnePageFinishOrders(pageIndex, pageSize,loginBaberId);
	}	
	
	public Page<Map<String,Object>> queryOnePageEvalateOrders(int pageIndex, int pageSize,long loginBaberId) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.queryOnePageEvaluateOrders(pageIndex, pageSize,loginBaberId);
	}	
}

