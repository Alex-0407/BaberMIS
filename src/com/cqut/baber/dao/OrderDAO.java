package com.cqut.baber.dao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseDao;

public class OrderDAO extends BaseDao {
	
	//admin
	public Page<Map<String, Object>> queryOnePageCheckOrders(int pageIndex,
			int pageSize, String orderStatus) throws SQLException,
			UnsupportedEncodingException {
		int startIndex = (pageIndex - 1) * pageSize;
		String condition = " where orderStatus='预约成功！' order by orderStartTime desc";
	
		Page<Map<String, Object>> page = this.queryWithLimit(
				"select count(*) from t_order" + condition,
				"select * from t_order" + condition,
				new HashMap<String, Object>(), startIndex, pageSize);
		return page;
	}
	
	public Page<Map<String, Object>> queryOnePageApplyDrawbackOrders(int pageIndex,
			int pageSize, String orderStatus) throws SQLException,
			UnsupportedEncodingException {
		int startIndex = (pageIndex - 1) * pageSize;
		String condition = " where orderStatus='用户申请退款！'";
	
		Page<Map<String, Object>> page = this.queryWithLimit(
				"select count(*) from t_order" + condition,
				"select * from t_order" + condition,
				new HashMap<String, Object>(), startIndex, pageSize);
		return page;
	}
	
	//baber
	public Page<Map<String, Object>> queryOnePagePreOrders(int pageIndex,
			int pageSize, long loginBaberId) throws SQLException,
			UnsupportedEncodingException {
		int startIndex = (pageIndex - 1) * pageSize;
		String condition = " where t1.baberId='"+loginBaberId+"'and t1.userId=t2.userId and t1.baberId=t3.baberId and orderStatus='现金已托管！' order by t1.orderStartTime desc";
	
		Page<Map<String, Object>> page = this.queryWithLimit(
				"select count(*) from t_order as t1,t_user as t2,t_baber as t3" + condition,
				"select * from t_order as t1,t_user as t2,t_baber as t3" + condition,
				new HashMap<String, Object>(), startIndex, pageSize);
		return page;
	}

	public Page<Map<String, Object>> queryOnePageOrders(int pageIndex,
			int pageSize, long loginBaberId) throws SQLException,
			UnsupportedEncodingException {
		int startIndex = (pageIndex - 1) * pageSize;
		String condition = " where t1.baberId='"+loginBaberId+"'and t1.userId=t2.userId and t1.baberId=t3.baberId and orderStatus='理发师已接单，请准时来店理发！' order by t1.orderStartTime desc";

		Page<Map<String, Object>> page = this.queryWithLimit(
				"select count(*) from t_order as t1,t_user as t2,t_baber as t3" + condition,
				"select * from t_order as t1,t_user as t2,t_baber as t3" + condition,
				new HashMap<String, Object>(), startIndex, pageSize);
		return page;
	}

	public Page<Map<String, Object>> queryOnePageEvaluateOrders(int pageIndex,
			int pageSize, long loginBaberId) throws SQLException,
			UnsupportedEncodingException {
		int startIndex = (pageIndex - 1) * pageSize;
		String condition = " where t1.baberId='"+loginBaberId+"'and t1.userId=t2.userId and t1.baberId=t3.baberId and orderStatus='支付成功，待评价！' order by t1.orderStartTime desc";

		Page<Map<String, Object>> page = this.queryWithLimit(
				"select count(*) from t_order as t1,t_user as t2,t_baber as t3" + condition,
				"select * from t_order as t1,t_user as t2,t_baber as t3" + condition,
				new HashMap<String, Object>(), startIndex, pageSize);
		return page;
	}
	
	public Page<Map<String, Object>> queryOnePageFinishOrders(int pageIndex,
			int pageSize, long loginBaberId) throws SQLException,
			UnsupportedEncodingException {
		int startIndex = (pageIndex - 1) * pageSize;
		String condition = " where t1.baberId='"+loginBaberId+"'and t1.userId=t2.userId and t1.baberId=t3.baberId and (orderStatus='已完成，未评价！' or orderStatus='已评价！' or orderStatus='用户已移除！') order by t1.orderStartTime desc";
	
		Page<Map<String, Object>> page = this.queryWithLimit(
				"select count(*) from t_order as t1,t_user as t2,t_baber as t3" + condition,
				"select * from t_order as t1,t_user as t2,t_baber as t3" + condition,
				new HashMap<String, Object>(), startIndex, pageSize);
		return page;
	}
}