package com.jobdiva.api.model.onboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class OnBoardPackage implements Serializable {
	
	private Long					id;
	private String					name;
	private List<OnBoardDocument>	documents;
	private Boolean					hirePackage			= false;
	private Boolean					supplementalPackage	= false;
	
	public void addDocument(OnBoardDocument onBoardDocument) {
		if (this.documents == null)
			this.documents = new ArrayList<>();
		this.documents.add(onBoardDocument);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<OnBoardDocument> getDocuments() {
		return this.documents;
	}
	
	public void setDocuments(List<OnBoardDocument> documents) {
		this.documents = documents;
	}
	
	public Boolean getHirePackage() {
		return this.hirePackage;
	}
	
	public void setHirePackage(Boolean hirePackage) {
		this.hirePackage = hirePackage;
	}
	
	public Boolean getSupplementalPackage() {
		return this.supplementalPackage;
	}
	
	public void setSupplementalPackage(Boolean supplementalPackage) {
		this.supplementalPackage = supplementalPackage;
	}
}
