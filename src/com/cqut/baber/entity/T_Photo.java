package com.cqut.baber.entity;

import java.io.Serializable;

import com.cqut.genhoo.annotation.CQUTColumn;
import com.cqut.genhoo.annotation.ID;
import com.cqut.genhoo.annotation.SqlType;

public class T_Photo implements Serializable {
	private static final long serialVersionUID = 8795307428819713219L;

	@CQUTColumn(name="photoId",label = "primary key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="photoId")
	private java.lang.Long photoId;
	
	@CQUTColumn(name="baberId",label = "foreign key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="baberId")
	private java.lang.Long baberId;

	@CQUTColumn(name="hairPhotoUrl",label = "作品图片地址",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String hairPhotoUrl;
	
	@CQUTColumn(name="hairPhotoName",label = "作品名称",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String hairPhotoName;
	
	@CQUTColumn(name="hairPhotoDescribe",label = "作品描述",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String hairPhotoDescribe;

	public java.lang.Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(java.lang.Long photoId) {
		this.photoId = photoId;
	}

	public java.lang.Long getBaberId() {
		return baberId;
	}

	public void setBaberId(java.lang.Long baberId) {
		this.baberId = baberId;
	}

	public java.lang.String getHairPhotoUrl() {
		return hairPhotoUrl;
	}

	public void setHairPhoto(java.lang.String hairPhotoUrl) {
		this.hairPhotoUrl = hairPhotoUrl;
	}

	public java.lang.String getHairPhotoName() {
		return hairPhotoName;
	}

	public void setHairPhotoName(java.lang.String hairPhotoName) {
		this.hairPhotoName = hairPhotoName;
	}

	public java.lang.String getHairPhotoDescribe() {
		return hairPhotoDescribe;
	}

	public void setHairPhotoDescribe(java.lang.String hairPhotoDescribe) {
		this.hairPhotoDescribe = hairPhotoDescribe;
	}
}
