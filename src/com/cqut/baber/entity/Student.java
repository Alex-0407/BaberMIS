package com.cqut.baber.entity;

import java.io.Serializable;

import com.cqut.genhoo.annotation.CQUTColumn;
import com.cqut.genhoo.annotation.ID;
import com.cqut.genhoo.annotation.SqlType;

public class Student implements Serializable{

	private static final long serialVersionUID = 8795307428819713219L;

	@CQUTColumn(name="studentID",label = "primary key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="studentID")
	private java.lang.Long studentID;
	
	@CQUTColumn(name="name",label = "姓名",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= false)
	private java.lang.String name;
	
	@CQUTColumn(name="code",label = "学号",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= false)
	private java.lang.String code;
	
	@CQUTColumn(name="remark",label = "学号",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= false)
	private java.lang.String remark;

	public java.lang.Long getStudentID() {
		return studentID;
	}

	public void setStudentID(java.lang.Long studentID) {
		this.studentID = studentID;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}	
}
