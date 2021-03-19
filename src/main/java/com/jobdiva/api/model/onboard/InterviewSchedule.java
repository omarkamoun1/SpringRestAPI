package com.jobdiva.api.model.onboard;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class InterviewSchedule implements Serializable {
	
	@JsonProperty(value = "candidateId", index = 0)
	private Long				candidateId;
	//
	@JsonProperty(value = "jobId", index = 1)
	private Long				jobId;
	//
	@JsonProperty(value = "label", index = 2)
	private String				label;
	//
	@JsonProperty(value = "hireTypeId", index = 3)
	private Long				hireTypeId;
	//
	@JsonProperty(value = "remark", index = 4)
	private String				remark;
	//
	@JsonProperty(value = "supplierId", index = 5)
	private Long				supplierId;
	//
	@JsonProperty(value = "candidateDocuments", index = 6)
	private List<Long>			candidateDocuments;
	//
	@JsonProperty(value = "supplementalPackages", index = 7)
	private List<SuppPackage>	supplementalPackages;
	
	public Boolean checkDocumentExistsInCandidateDocuments(Long tabid, Long docId) {
		if (this.candidateDocuments != null)
			for (Long onBoardDocId : this.candidateDocuments) {
				if (onBoardDocId != null && onBoardDocId.equals(docId))
					return true;
			}
		return false;
	}
	
	public Boolean checkDocumentExistsSupppackages(Long tabid, Long docId) {
		if (this.supplementalPackages != null)
			for (SuppPackage suppPackage : this.supplementalPackages) {
				if (suppPackage.getDocumentList() != null)
					for (Long documentId : suppPackage.getDocumentList()) {
						if (documentId != null && documentId.equals(docId))
							return true;
					}
			}
		return false;
	}
	
	public Long getCandidateId() {
		return this.candidateId;
	}
	
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Long getHireTypeId() {
		return this.hireTypeId;
	}
	
	public void setHireTypeId(Long hireTypeId) {
		this.hireTypeId = hireTypeId;
	}
	
	public Long getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
	public Long getJobId() {
		return this.jobId;
	}
	
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	
	public List<Long> getCandidateDocuments() {
		return this.candidateDocuments;
	}
	
	public void setCandidateDocuments(List<Long> candidateDocuments) {
		this.candidateDocuments = candidateDocuments;
	}
	
	public List<SuppPackage> getSupplementalPackages() {
		return this.supplementalPackages;
	}
	
	public void setSupplementalPackages(List<SuppPackage> supplementalPackages) {
		this.supplementalPackages = supplementalPackages;
	}
}
