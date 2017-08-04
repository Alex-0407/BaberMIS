package com.cqut.baber.entity;

import java.io.Serializable;

import com.cqut.genhoo.annotation.CQUTColumn;
import com.cqut.genhoo.annotation.ID;
import com.cqut.genhoo.annotation.SqlType;

public class T_Service implements Serializable{

	private static final long serialVersionUID = 8795307428819713219L;

	@CQUTColumn(name="serviceId",label = "primary key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="serviceId")
	private java.lang.Long serviceId;
	
	@CQUTColumn(name="serviceName",label = "服务类型名",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= false)
	private java.lang.String serviceName;
	
	@CQUTColumn(name="nameIntro",label = "理发介绍",type = SqlType.VARCHAR,length = 255,isReference = false,nullable= false)
	private java.lang.String nameIntro;
	
	@CQUTColumn(name="timeIntro",label = "时间介绍",type = SqlType.VARCHAR,length = 255,isReference = false,nullable= false)
	private java.lang.String timeIntro;
	
	@CQUTColumn(name="hairStyle",label = "发型介绍",type = SqlType.VARCHAR,length = 255,isReference = false,nullable= false)
	private java.lang.String hairStyle;
	
	@CQUTColumn(name="extraService",label = "赠送服务",type = SqlType.VARCHAR,length = 255,isReference = false,nullable= false)
	private java.lang.String extraService;
	
	@CQUTColumn(name="serviceImgUrl",label = "图片",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= false)
	private java.lang.String serviceImgUrl;
	
	@CQUTColumn(name="serviceStatus",label = "服务状态",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= false)
	private java.lang.String serviceStatus;

	public java.lang.Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(java.lang.Long serviceId) {
		this.serviceId = serviceId;
	}

	public java.lang.String getServiceName() {
		return serviceName;
	}

	public void setServiceName(java.lang.String serviceName) {
		this.serviceName = serviceName;
	}

	public java.lang.String getNameIntro() {
		return nameIntro;
	}

	public void setNameIntro(java.lang.String nameIntro) {
		this.nameIntro = nameIntro;
	}

	public java.lang.String getTimeIntro() {
		return timeIntro;
	}

	public void setTimeIntro(java.lang.String timeIntro) {
		this.timeIntro = timeIntro;
	}

	public java.lang.String getHairStyle() {
		return hairStyle;
	}

	public void setHairStyle(java.lang.String hairStyle) {
		this.hairStyle = hairStyle;
	}

	public java.lang.String getExtraService() {
		return extraService;
	}

	public void setExtraService(java.lang.String extraService) {
		this.extraService = extraService;
	}

	public java.lang.String getServiceImgUrl() {
		return serviceImgUrl;
	}

	public void setServiceImgUrl(java.lang.String serviceImgUrl) {
		this.serviceImgUrl = serviceImgUrl;
	}

	public java.lang.String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(java.lang.String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
}
