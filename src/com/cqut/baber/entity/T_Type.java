package com.cqut.baber.entity;

import java.io.Serializable;

import com.cqut.genhoo.annotation.CQUTColumn;
import com.cqut.genhoo.annotation.ID;
import com.cqut.genhoo.annotation.SqlType;

public class T_Type implements Serializable{

	private static final long serialVersionUID = 8795307428819713219L;

	@CQUTColumn(name="typeId",label = "primary key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="typeId")
	private java.lang.Long typeId;
	
	@CQUTColumn(name="typeName",label = "类型名",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= false)
	private java.lang.String typeName;
	
	@CQUTColumn(name="typeStatus",label = "状态",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= false)
	private java.lang.String typeStatus;

	public java.lang.Long getTypeId() {
		return typeId;
	}

	public void setTypeId(java.lang.Long typeId) {
		this.typeId = typeId;
	}

	public java.lang.String getTypeName() {
		return typeName;
	}

	public void setTypeName(java.lang.String typeName) {
		this.typeName = typeName;
	}

	public java.lang.String getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(java.lang.String typeStatus) {
		this.typeStatus = typeStatus;
	}
}
