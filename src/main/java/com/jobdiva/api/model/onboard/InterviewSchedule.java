package com.jobdiva.api.model.onboard;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class InterviewSchedule implements Serializable {
	
	private Long					candidateId;
	private String					label;
	private String					remark;
	private Long					hireTypeId;
	private Long					supplierId;
	private Long					jobId;
	private List<OnBoardDocument>	candidateDocuments;
	private List<SuppPackage>		supplementalPackages;
	
	public Boolean checkDocumentExistsInCandidateDocuments(Long tabid, Long docId) {
		if (this.candidateDocuments != null)
			for (OnBoardDocument onBoardDocument : this.candidateDocuments) {
				if (onBoardDocument.getId() != null && onBoardDocument.getId().equals(docId))
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
	
	public List<OnBoardDocument> getCandidateDocuments() {
		return this.candidateDocuments;
	}
	
	public void setCandidateDocuments(List<OnBoardDocument> candidateDocuments) {
		this.candidateDocuments = candidateDocuments;
	}
	
	public List<SuppPackage> getSupplementalPackages() {
		return this.supplementalPackages;
	}
	
	public void setSupplementalPackages(List<SuppPackage> supplementalPackages) {
		this.supplementalPackages = supplementalPackages;
	}
}
