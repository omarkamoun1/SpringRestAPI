package com.jobdiva.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class Attachment implements java.io.Serializable {
	
	@ApiModelProperty(name = "entityId")
	private Long	entityId;
	//
	@ApiModelProperty(name = "fileId")
	private Long	fileId;
	//
	@ApiModelProperty(name = "fileName")
	private String	fileName;
	//
	@ApiModelProperty(name = "fileData")
	private byte[]	fileData;
	//
	@ApiModelProperty(name = "description")
	private String	description;
	//
	@ApiModelProperty(name = "fileType")
	private String	fileType;
	
	@Override
	public String toString() {
		return "Attachment [entityId=" + entityId + ", fileId=" + fileId + ", fileName=" + fileName + " , fileData=" + fileData + ", description=" + description + ", fileType=" + fileType + "]";
	}
	
	public Long getEntityId() {
		return entityId;
	}
	
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	
	public Long getFileId() {
		return fileId;
	}
	
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public byte[] getFileData() {
		return fileData;
	}
	
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFileType() {
		return fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
