package com.jobdiva.api.model;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class SocialNetworkType implements java.io.Serializable {
	
	//
//	@JsonProperty(value = "name", index = 0, required = true)
	private String	name;
	//
//	@JsonProperty(value = "link", index = 1, required = true)
	private String	link;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
}
