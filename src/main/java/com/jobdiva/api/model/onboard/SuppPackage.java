package com.jobdiva.api.model.onboard;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class SuppPackage implements Serializable {
	
	private Long		packageId;
	private List<Long>	documentList;
	
	public Long getPackageId() {
		return this.packageId;
	}
	
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	
	public List<Long> getDocumentList() {
		return this.documentList;
	}
	
	public void setDocumentList(List<Long> documentList) {
		this.documentList = documentList;
	}
}
