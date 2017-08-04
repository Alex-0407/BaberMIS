package com.cqut.baber.entity;

import java.io.Serializable;

import com.cqut.genhoo.annotation.CQUTColumn;
import com.cqut.genhoo.annotation.ID;
import com.cqut.genhoo.annotation.SqlType;

public class T_Admin implements Serializable{

	private static final long serialVersionUID = 8795307428819713219L;

	@CQUTColumn(name="adminId",label = "primary key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="adminId")
	private java.lang.Long adminId;

	@CQUTColumn(name="adminName",label = "用户名",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String adminName;
	
	@CQUTColumn(name="adminPwd",label = "密码",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String adminPwd;
	
	@CQUTColumn(name="adminRealName",label = "真实姓名",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String adminRealName;
	
	@CQUTColumn(name="adminSex",label = "性别",type = SqlType.CHAR,length = 4,isReference = false,nullable= true)
	private java.lang.String adminSex;
	
	@CQUTColumn(name="adminAge",label = "年龄",type = SqlType.INTEGER,length = 4,isReference = false,nullable= true)
	private java.lang.Integer adminAge;
	
	@CQUTColumn(name="adminTel",label = "电话",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String adminTel;
	
	@CQUTColumn(name="adminAddress",label = "地址",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String adminAddress;
	
	@CQUTColumn(name="adminAccountName",label = "账户名",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String adminAccountName;
	
	@CQUTColumn(name="adminAccountAmount",label = "账户总额",type = SqlType.FLOAT,length = 50,isReference = false,nullable= true)
	private java.lang.Float adminAccountAmount;
	
	@CQUTColumn(name="adminStatus",label = "状态",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String adminStatus;

	public java.lang.Long getAdminId() {
		return adminId;
	}

	public void setAdminId(java.lang.Long adminId) {
		this.adminId = adminId;
	}

	public java.lang.String getAdminName() {
		return adminName;
	}

	public void setAdminName(java.lang.String adminName) {
		this.adminName = adminName;
	}

	public java.lang.String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(java.lang.String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public java.lang.String getAdminRealName() {
		return adminRealName;
	}

	public void setAdminRealName(java.lang.String adminRealName) {
		this.adminRealName = adminRealName;
	}

	public java.lang.String getAdminSex() {
		return adminSex;
	}

	public void setAdminSex(java.lang.String adminSex) {
		this.adminSex = adminSex;
	}

	public java.lang.Integer getAdminAge() {
		return adminAge;
	}

	public void setAdminAge(java.lang.Integer adminAge) {
		this.adminAge = adminAge;
	}

	public java.lang.String getAdminTel() {
		return adminTel;
	}

	public void setAdminTel(java.lang.String adminTel) {
		this.adminTel = adminTel;
	}

	public java.lang.String getAdminAddress() {
		return adminAddress;
	}

	public void setAdminAddress(java.lang.String adminAddress) {
		this.adminAddress = adminAddress;
	}

	public java.lang.String getAdminAccountName() {
		return adminAccountName;
	}

	public void setAdminAccountName(java.lang.String adminAccountName) {
		this.adminAccountName = adminAccountName;
	}

	public java.lang.Float getAdminAccountAmount() {
		return adminAccountAmount;
	}

	public void setAdminAccountAmount(java.lang.Float adminAccountAmount) {
		this.adminAccountAmount = adminAccountAmount;
	}

	public java.lang.String getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(java.lang.String adminStatus) {
		this.adminStatus = adminStatus;
	}
}
	