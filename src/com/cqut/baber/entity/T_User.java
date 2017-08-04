package com.cqut.baber.entity;

import java.io.Serializable;

import com.cqut.genhoo.annotation.CQUTColumn;
import com.cqut.genhoo.annotation.ID;
import com.cqut.genhoo.annotation.SqlType;

public class T_User implements Serializable{

	private static final long serialVersionUID = 8795307428819713219L;

	@CQUTColumn(name="userId",label = "primary key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="userId")
	private java.lang.Long userId;
	
	@CQUTColumn(name="userName",label = "用户名",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= false)
	private java.lang.String userName;
	
	@CQUTColumn(name="userPwd",label = "密码",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= false)
	private java.lang.String userPwd;
	
	@CQUTColumn(name="userAccountName",label = "账户名",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= false)
	private java.lang.String userAccountName;
	
	@CQUTColumn(name="userAccountAmount",label = "账户总额",type = SqlType.FLOAT,length = 50,isReference = false,nullable= false)
	private java.lang.Float userAccountAmount;
	
	@CQUTColumn(name="userTel",label = "手机号",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= false)
	private java.lang.String userTel;
	
	@CQUTColumn(name="userRealName",label = "真实姓名",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= false)
	private java.lang.String userRealName;
	
	@CQUTColumn(name="userSex",label = "性别",type = SqlType.CHAR,length = 20,isReference = false,nullable= false)
	private java.lang.String userSex;
	
	@CQUTColumn(name="userAge",label = "年龄",type = SqlType.INTEGER,length = 50,isReference = false,nullable= false)
	private java.lang.Integer userAge;
	
	@CQUTColumn(name="userBirthday",label = "生日",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= false)
	private java.lang.String userBirthday;
	
	@CQUTColumn(name="userAddress",label = "地址",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= false)
	private java.lang.String userAddress;
	
	@CQUTColumn(name="userPhotoUrl",label = "个人头像",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= false)
	private java.lang.String userPhotoUrl;
	
	@CQUTColumn(name="userStatus",label = "状态",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= false)
	private java.lang.String userStatus;

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(java.lang.String userPwd) {
		this.userPwd = userPwd;
	}

	public java.lang.String getUserAccountName() {
		return userAccountName;
	}

	public void setUserAccountName(java.lang.String userAccountName) {
		this.userAccountName = userAccountName;
	}

	public java.lang.Float getUserAccountAmount() {
		return userAccountAmount;
	}

	public void setUserAccountAmount(java.lang.Float userAccountAmount) {
		this.userAccountAmount = userAccountAmount;
	}

	public java.lang.String getUserTel() {
		return userTel;
	}

	public void setUserTel(java.lang.String userTel) {
		this.userTel = userTel;
	}

	public java.lang.String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(java.lang.String userRealName) {
		this.userRealName = userRealName;
	}

	public java.lang.String getUserSex() {
		return userSex;
	}

	public void setUserSex(java.lang.String userSex) {
		this.userSex = userSex;
	}

	public java.lang.Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(java.lang.Integer userAge) {
		this.userAge = userAge;
	}

	public java.lang.String getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(java.lang.String userBirthday) {
		this.userBirthday = userBirthday;
	}

	public java.lang.String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(java.lang.String userAddress) {
		this.userAddress = userAddress;
	}

	public java.lang.String getUserPhotoUrl() {
		return userPhotoUrl;
	}

	public void setUserPhotoUrl(java.lang.String userPhotoUrl) {
		this.userPhotoUrl = userPhotoUrl;
	}

	public java.lang.String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(java.lang.String userStatus) {
		this.userStatus = userStatus;
	}

	
}
