package com.cqut.baber.entity;

import java.io.Serializable;

import com.cqut.genhoo.annotation.CQUTColumn;
import com.cqut.genhoo.annotation.ID;
import com.cqut.genhoo.annotation.SqlType;

public class T_Order implements Serializable{

	private static final long serialVersionUID = 8795307428819713219L;

	@CQUTColumn(name="orderId",label = "primary key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="orderId")
	private java.lang.Long orderId;
	
	@CQUTColumn(name="baberId",label = "foreign key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="baberId")
	private java.lang.Long baberId;
	
	@CQUTColumn(name="userId",label = "foreign key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="userId")
	private java.lang.Long userId;

	@CQUTColumn(name="orderCode",label = "订单编号",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String orderCode;
	
	@CQUTColumn(name="appointmentTime",label = "预约时间",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String appointmentTime;
	
	@CQUTColumn(name="orderStartTime",label = "订单开始时间",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String orderStartTime;
	
	@CQUTColumn(name="orderEndTime",label = "订单结束时间",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String orderEndTime;
	
	@CQUTColumn(name="reservateServe",label = "订单服务",type = SqlType.CHAR,length = 4,isReference = false,nullable= true)
	private java.lang.String reservateServe;
	
	@CQUTColumn(name="orderPrice",label = "订单价格",type = SqlType.INTEGER,length = 4,isReference = false,nullable= true)
	private java.lang.Integer orderPrice;
	
	@CQUTColumn(name="orderStatus",label = "订单状态",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String orderStatus;

	public java.lang.Long getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.Long orderId) {
		this.orderId = orderId;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(java.lang.String orderCode) {
		this.orderCode = orderCode;
	}

	public java.lang.String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(java.lang.String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public java.lang.String getOrderEndTime() {
		return orderEndTime;
	}

	public void setOrderEndTime(java.lang.String orderEndTime) {
		this.orderEndTime = orderEndTime;
	}

	public java.lang.String getReservateServe() {
		return reservateServe;
	}

	public void setReservateServe(java.lang.String reservateServe) {
		this.reservateServe = reservateServe;
	}

	public java.lang.Integer getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(java.lang.Integer orderPrice) {
		this.orderPrice = orderPrice;
	}

	public java.lang.String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(java.lang.String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public java.lang.Long getBaberId() {
		return baberId;
	}

	public void setBaberId(java.lang.Long baberId) {
		this.baberId = baberId;
	}

	public java.lang.String getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(java.lang.String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}
}
	