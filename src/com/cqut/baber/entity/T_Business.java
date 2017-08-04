package com.cqut.baber.entity;

import java.io.Serializable;

import com.cqut.genhoo.annotation.CQUTColumn;
import com.cqut.genhoo.annotation.ID;
import com.cqut.genhoo.annotation.SqlType;

public class T_Business implements Serializable{

	private static final long serialVersionUID = 8795307428819713219L;

	@CQUTColumn(name="businessId",label = "primary key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="businessId")
	private java.lang.Long businessId;
	
	@CQUTColumn(name="baberId",label = "foreign key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="baberId")
	private java.lang.Long baberId;

	@CQUTColumn(name="serviceType",label = "服务类型",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String serviceType;
	
	@CQUTColumn(name="servicePrice",label = "服务价格",type = SqlType.FLOAT,length = 20,isReference = false,nullable= true)
	private java.lang.Float servicePrice;
	
	@CQUTColumn(name="serviceLevel",label = "服务级别",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String serviceLevel;
	
	@CQUTColumn(name="serviceTime",label = "服务时间",type = SqlType.CHAR,length = 4,isReference = false,nullable= true)
	private java.lang.String serviceTime;
	
	@CQUTColumn(name="serviceStatus",label = "服务状态",type = SqlType.INTEGER,length = 4,isReference = false,nullable= true)
	private java.lang.String serviceStatus;

	public java.lang.Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(java.lang.Long businessId) {
		this.businessId = businessId;
	}

	public java.lang.Long getBaberId() {
		return baberId;
	}

	public void setBaberId(java.lang.Long baberId) {
		this.baberId = baberId;
	}

	public java.lang.String getServiceType() {
		return serviceType;
	}

	public void setServiceType(java.lang.String serviceType) {
		this.serviceType = serviceType;
	}

	public Float getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(Float servicePrice) {
		this.servicePrice = servicePrice;
	}

	public java.lang.String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(java.lang.String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public java.lang.String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(java.lang.String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(java.lang.String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
}
	