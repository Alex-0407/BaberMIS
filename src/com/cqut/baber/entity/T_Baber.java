package com.cqut.baber.entity;

import java.io.Serializable;

import com.cqut.genhoo.annotation.CQUTColumn;
import com.cqut.genhoo.annotation.ID;
import com.cqut.genhoo.annotation.SqlType;

public class T_Baber implements Serializable{

	private static final long serialVersionUID = 8795307428819713219L;

	@CQUTColumn(name="baberId",label = "primary key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="baberId")
	private java.lang.Long baberId;

	@CQUTColumn(name="baberName",label = "用户名",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String baberName;
	
	@CQUTColumn(name="baberPwd",label = "密码",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String baberPwd;
	
	@CQUTColumn(name="realName",label = "真实姓名",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String realName;
	
	@CQUTColumn(name="baberSex",label = "性别",type = SqlType.CHAR,length = 4,isReference = false,nullable= true)
	private java.lang.String baberSex;
	
	@CQUTColumn(name="baberAge",label = "年龄",type = SqlType.INTEGER,length = 4,isReference = false,nullable= true)
	private java.lang.Integer baberAge;
	
	@CQUTColumn(name="baberTel",label = "电话",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String baberTel;
	
	@CQUTColumn(name="baberAddress",label = "地址",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String baberAddress;
	
	@CQUTColumn(name="baberIDCard",label = "身份证号",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String baberIDCard;
	
	@CQUTColumn(name="baberPhotoUrl",label = "身份证号",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String baberPhotoUrl;
	
	@CQUTColumn(name="baberWorkTime",label = "从业时间",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String baberWorkTime;
	
	@CQUTColumn(name="baberAccountName",label = "账户名",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String baberAccountName;
	
	@CQUTColumn(name="baberAccountAmount",label = "账户总额",type = SqlType.FLOAT,length = 50,isReference = false,nullable= true)
	private java.lang.Float baberAccountAmount;
	
	@CQUTColumn(name="baberPlace",label = "地区",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String baberPlace;
	
	@CQUTColumn(name="baberService",label = "服务",type = SqlType.LONGVARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String baberService;
	
	@CQUTColumn(name="baberSelfIntroduce",label = "简介",type = SqlType.LONGVARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String baberSelfIntroduce;
	
	@CQUTColumn(name="baberWorkExperience",label = "从业经历",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String baberWorkExperience;
	
	@CQUTColumn(name="baberStatus",label = "状态",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String baberStatus;

	public java.lang.Long getBaberId() {
		return baberId;
	}

	public void setBaberId(java.lang.Long baberId) {
		this.baberId = baberId;
	}

	public java.lang.String getBaberName() {
		return baberName;
	}

	public void setBaberName(java.lang.String baberName) {
		this.baberName = baberName;
	}

	public java.lang.String getBaberPwd() {
		return baberPwd;
	}

	public void setBaberPwd(java.lang.String baberPwd) {
		this.baberPwd = baberPwd;
	}

	public java.lang.String getRealName() {
		return realName;
	}

	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}

	public java.lang.String getBaberSex() {
		return baberSex;
	}

	public void setBaberSex(java.lang.String baberSex) {
		this.baberSex = baberSex;
	}

	public java.lang.Integer getBaberAge() {
		return baberAge;
	}

	public void setBaberAge(java.lang.Integer baberAge) {
		this.baberAge = baberAge;
	}

	public java.lang.String getBaberTel() {
		return baberTel;
	}

	public void setBaberTel(java.lang.String baberTel) {
		this.baberTel = baberTel;
	}

	public java.lang.String getBaberAddress() {
		return baberAddress;
	}

	public void setBaberAddress(java.lang.String baberAddress) {
		this.baberAddress = baberAddress;
	}

	public java.lang.String getBaberIDCard() {
		return baberIDCard;
	}

	public void setBaberIDCard(java.lang.String baberIDCard) {
		this.baberIDCard = baberIDCard;
	}

	public java.lang.String getBaberPhotoUrl() {
		return baberPhotoUrl;
	}

	public void setBaberphotoUrl(java.lang.String baberPhotoUrl) {
		this.baberPhotoUrl = baberPhotoUrl;
	}

	public java.lang.String getBaberWorkTime() {
		return baberWorkTime;
	}

	public void setBaberWorkTime(java.lang.String baberWorkTime) {
		this.baberWorkTime = baberWorkTime;
	}

	public java.lang.String getBaberAccountName() {
		return baberAccountName;
	}

	public void setBaberAccountName(java.lang.String baberAccountName) {
		this.baberAccountName = baberAccountName;
	}

	public java.lang.Float getBaberAccountAmount() {
		return baberAccountAmount;
	}

	public void setBaberAccountAmount(java.lang.Float baberAccountAmount) {
		this.baberAccountAmount = baberAccountAmount;
	}

	public java.lang.String getBaberPlace() {
		return baberPlace;
	}

	public void setBaberPlace(java.lang.String baberPlace) {
		this.baberPlace = baberPlace;
	}

	public java.lang.String getBaberService() {
		return baberService;
	}

	public void setBaberService(java.lang.String baberService) {
		this.baberService = baberService;
	}

	public java.lang.String getBaberSelfIntroduce() {
		return baberSelfIntroduce;
	}

	public void setBaberSelfIntroduce(java.lang.String baberSelfIntroduce) {
		this.baberSelfIntroduce = baberSelfIntroduce;
	}

	public java.lang.String getBaberWorkExperience() {
		return baberWorkExperience;
	}

	public void setBaberWorkExperience(java.lang.String baberWorkExperience) {
		this.baberWorkExperience = baberWorkExperience;
	}

	public java.lang.String getBaberStatus() {
		return baberStatus;
	}

	public void setBaberStatus(java.lang.String baberStatus) {
		this.baberStatus = baberStatus;
	}

	
}