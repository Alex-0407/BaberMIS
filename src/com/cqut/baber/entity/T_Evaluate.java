package com.cqut.baber.entity;

import java.io.Serializable;

import com.cqut.genhoo.annotation.CQUTColumn;
import com.cqut.genhoo.annotation.ID;
import com.cqut.genhoo.annotation.SqlType;

public class T_Evaluate implements Serializable{

	private static final long serialVersionUID = 8795307428819713219L;

	@CQUTColumn(name="evaluateId",label = "primary key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="evaluateId")
	private java.lang.Long evaluateId;
	
	@CQUTColumn(name="orderId",label = "foreign key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="orderId")
	private java.lang.Long orderId;
	
	@CQUTColumn(name="serviceAttitudeScore",label = "",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String serviceAttitudeScore;
	
	@CQUTColumn(name="professionalSkillScore",label = "",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String professionalSkillScore;
	
	@CQUTColumn(name="serviceEnvironmentScore",label = "",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String serviceEnvironmentScore;
	
	@CQUTColumn(name="onTimeScore",label = "",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String onTimeScore;
	
	@CQUTColumn(name="compositeScore",label = "",type = SqlType.CHAR,length = 4,isReference = false,nullable= true)
	private java.lang.String compositeScore;
	
	@CQUTColumn(name="evaluateContent",label = "",type = SqlType.INTEGER,length = 4,isReference = false,nullable= true)
	private java.lang.String evaluateContent;
	
	@CQUTColumn(name="evaluateTime",label = "",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String evaluateTime;
	
	@CQUTColumn(name="reviewContent",label = "",type = SqlType.INTEGER,length = 4,isReference = false,nullable= true)
	private java.lang.String reviewContent;
	
	@CQUTColumn(name="reviewTime",label = "",type = SqlType.VARCHAR,length = 50,isReference = false,nullable= true)
	private java.lang.String reviewTime;

	public java.lang.Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(java.lang.Long evaluateId) {
		this.evaluateId = evaluateId;
	}

	public java.lang.Long getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.Long orderId) {
		this.orderId = orderId;
	}

	public java.lang.String getServiceAttitudeScore() {
		return serviceAttitudeScore;
	}

	public void setServiceAttitudeScore(java.lang.String serviceAttitudeScore) {
		this.serviceAttitudeScore = serviceAttitudeScore;
	}

	public java.lang.String getProfessionalSkillScore() {
		return professionalSkillScore;
	}

	public void setProfessionalSkillScore(java.lang.String professionalSkillScore) {
		this.professionalSkillScore = professionalSkillScore;
	}

	public java.lang.String getServiceEnvironmentScore() {
		return serviceEnvironmentScore;
	}

	public void setServiceEnvironmentScore(java.lang.String serviceEnvironmentScore) {
		this.serviceEnvironmentScore = serviceEnvironmentScore;
	}

	public java.lang.String getOnTimeScore() {
		return onTimeScore;
	}

	public void setOnTimeScore(java.lang.String onTimeScore) {
		this.onTimeScore = onTimeScore;
	}

	public java.lang.String getCompositeScore() {
		return compositeScore;
	}

	public void setCompositeScore(java.lang.String compositeScore) {
		this.compositeScore = compositeScore;
	}

	public java.lang.String getEvaluateContent() {
		return evaluateContent;
	}

	public void setEvaluateContent(java.lang.String evaluateContent) {
		this.evaluateContent = evaluateContent;
	}

	public java.lang.String getEvaluateTime() {
		return evaluateTime;
	}

	public void setEvaluateTime(java.lang.String evaluateTime) {
		this.evaluateTime = evaluateTime;
	}

	public java.lang.String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(java.lang.String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public java.lang.String getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(java.lang.String reviewTime) {
		this.reviewTime = reviewTime;
	}
}
	