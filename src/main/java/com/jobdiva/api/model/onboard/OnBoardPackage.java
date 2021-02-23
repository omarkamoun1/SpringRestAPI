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
	private String					packageType;
	
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
	
	public String getPackageType() {
		return packageType;
	}
	
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
}
