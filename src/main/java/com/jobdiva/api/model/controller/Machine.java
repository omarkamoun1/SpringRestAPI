package com.jobdiva.api.model.controller;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Machine implements Serializable {
	
	private String machineId;
	
	public String getMachineId() {
		return machineId;
	}
	
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
}
