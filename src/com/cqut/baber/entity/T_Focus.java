package com.cqut.baber.entity;

import java.io.Serializable;

import com.cqut.genhoo.annotation.CQUTColumn;
import com.cqut.genhoo.annotation.ID;
import com.cqut.genhoo.annotation.SqlType;

public class T_Focus implements Serializable{
	private static final long serialVersionUID = 8795307428819713219L;
	
	@CQUTColumn(name="focusId",label = "primary key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="focusId")
	private java.lang.Long focusId;
	
	@CQUTColumn(name="userId",label = "foreign key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="userId")
	private java.lang.Long userId;
	
	@CQUTColumn(name="baberId",label = "foreign key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="baberId")
	private java.lang.Long baberId;
	
	@CQUTColumn(name="focusBarberPhoto",label = "",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String focusBarberPhoto;
	
	@CQUTColumn(name="focusSloganWord",label = "",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String focusSloganWord;
	
	@CQUTColumn(name="focusPrice",label = "",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String focusPrice;
	
	@CQUTColumn(name="focusTime",label = "",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String focusTime;
	
	@CQUTColumn(name="focusStatus",label = "",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String focusStatus;

	public java.lang.Long getFocusId() {
		return focusId;
	}

	public void setFocusId(java.lang.Long focusId) {
		this.focusId = focusId;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.Long getBaberId() {
		return baberId;
	}

	public void setBaberId(java.lang.Long baberId) {
		this.baberId = baberId;
	}

	public java.lang.String getFocusBarberPhoto() {
		return focusBarberPhoto;
	}

	public void setFocusBarberPhoto(java.lang.String focusBarberPhoto) {
		this.focusBarberPhoto = focusBarberPhoto;
	}

	public java.lang.String getFocusSloganWord() {
		return focusSloganWord;
	}

	public void setFocusSloganWord(java.lang.String focusSloganWord) {
		this.focusSloganWord = focusSloganWord;
	}

	public java.lang.String getFocusPrice() {
		return focusPrice;
	}

	public void setFocusPrice(java.lang.String focusPrice) {
		this.focusPrice = focusPrice;
	}

	public java.lang.String getFocusTime() {
		return focusTime;
	}

	public void setFocusTime(java.lang.String focusTime) {
		this.focusTime = focusTime;
	}

	public java.lang.String getFocusStatus() {
		return focusStatus;
	}

	public void setFocusStatus(java.lang.String focusStatus) {
		this.focusStatus = focusStatus;
	}
	
}
