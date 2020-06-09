package com.jobdiva.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel()
public class Skill implements java.io.Serializable {
	
	@ApiModelProperty(name = "skillType")
	private String skillType;
	
	@Override
	public String toString() {
		return "Skill [skillType=" + skillType + "]";
	}
	
	public String getSkillType() {
		return skillType;
	}
	
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}
}