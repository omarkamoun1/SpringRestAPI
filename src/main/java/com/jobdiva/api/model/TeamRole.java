package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class TeamRole implements java.io.Serializable {

    private Long					id;
 	
	private String				 	name;
	
	private String 				primary;					

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}
	
	
	
}
