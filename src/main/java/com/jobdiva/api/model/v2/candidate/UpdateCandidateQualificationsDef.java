package com.jobdiva.api.model.v2.candidate;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.QualificationType;

/**
 * @author Joseph Chidiac
 *
 *         Apr 12, 2021
 */
@SuppressWarnings("serial")
public class UpdateCandidateQualificationsDef implements Serializable {
	
	@JsonProperty(value = "candidateid", required = true) //
	private Long				candidateid;
	//
	@JsonProperty(value = "overwrite", required = true) //
	private Boolean				overwrite;
	//
	@JsonProperty(value = "qualifications", required = false) //
	private QualificationType[]	qualifications;
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public Boolean getOverwrite() {
		return overwrite;
	}
	
	public void setOverwrite(Boolean overwrite) {
		this.overwrite = overwrite;
	}
	
	public QualificationType[] getQualifications() {
		return qualifications;
	}
	
	public void setQualifications(QualificationType[] qualifications) {
		this.qualifications = qualifications;
	}
}
