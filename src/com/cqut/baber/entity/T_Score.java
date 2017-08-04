package com.cqut.baber.entity;

import java.io.Serializable;

import com.cqut.genhoo.annotation.CQUTColumn;
import com.cqut.genhoo.annotation.ID;
import com.cqut.genhoo.annotation.SqlType;

public class T_Score implements Serializable{

	private static final long serialVersionUID = 8795307428819713219L;

	@CQUTColumn(name="scoreId",label = "primary key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="scoreId")
	private java.lang.Long scoreId;
	
	@CQUTColumn(name="baberId",label = "foreign key",type = SqlType.BIGINT,length = 20,nullable= false)
	@ID(name="baberId")
	private java.lang.Long baberId;

	@CQUTColumn(name="serviceAttitudeScore",label = "订单编号",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String serviceAttitudeScore;
	
	@CQUTColumn(name="professionalSkillScore",label = "预约时间",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String professionalSkillScore;
	
	@CQUTColumn(name="serviceEnvironmentScore",label = "订单开始时间",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String serviceEnvironmentScore;
	
	@CQUTColumn(name="onTimeScores",label = "订单结束时间",type = SqlType.VARCHAR,length = 20,isReference = false,nullable= true)
	private java.lang.String onTimeScores;
	
	@CQUTColumn(name="compositeScore",label = "订单服务",type = SqlType.CHAR,length = 4,isReference = false,nullable= true)
	private java.lang.String compositeScore;

	public java.lang.Long getScoreId() {
		return scoreId;
	}

	public void setScoreId(java.lang.Long scoreId) {
		this.scoreId = scoreId;
	}

	public java.lang.Long getBaberId() {
		return baberId;
	}

	public void setBaberId(java.lang.Long baberId) {
		this.baberId = baberId;
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

	public java.lang.String getOnTimeScores() {
		return onTimeScores;
	}

	public void setOnTimeScores(java.lang.String onTimeScores) {
		this.onTimeScores = onTimeScores;
	}

	public java.lang.String getCompositeScore() {
		return compositeScore;
	}

	public void setCompositeScore(java.lang.String compositeScore) {
		this.compositeScore = compositeScore;
	}
}
	