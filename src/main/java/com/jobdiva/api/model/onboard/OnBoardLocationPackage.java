package com.jobdiva.api.model.onboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class OnBoardLocationPackage implements Serializable {
	
	private Boolean					cityPackage		= false;
	private Boolean					statePackage	= false;
	private Boolean					countryPackage	= false;
	private List<OnBoardDocument>	documents;
	
	public void addDocument(OnBoardDocument onBoardDocument) {
		if (this.documents == null)
			this.documents = new ArrayList<>();
		this.documents.add(onBoardDocument);
	}
	
	public Boolean getCityPackage() {
		return this.cityPackage;
	}
	
	public void setCityPackage(Boolean cityPackage) {
		this.cityPackage = cityPackage;
	}
	
	public Boolean getStatePackage() {
		return this.statePackage;
	}
	
	public void setStatePackage(Boolean statePackage) {
		this.statePackage = statePackage;
	}
	
	public Boolean getCountryPackage() {
		return this.countryPackage;
	}
	
	public void setCountryPackage(Boolean countryPackage) {
		this.countryPackage = countryPackage;
	}
	
	public List<OnBoardDocument> getDocuments() {
		return documents;
	}
	
	public void setDocuments(List<OnBoardDocument> documents) {
		this.documents = documents;
	}
}
