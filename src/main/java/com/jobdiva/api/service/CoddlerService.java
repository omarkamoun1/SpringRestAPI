package com.jobdiva.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jobdiva.api.dao.coddler.CoddlerSchedulerDao;
import com.jobdiva.api.model.controller.Coddler;
import com.jobdiva.api.model.controller.Configuration;

@Service
public class CoddlerService {
	
	//
	@Autowired
	CoddlerSchedulerDao coddlerSchedulerDao;
	
	//
	public List<Coddler> getCoddlers(Integer machineId, Long teamId) {
		//
		return teamId != null ? coddlerSchedulerDao.getCoddlers(machineId, teamId) : coddlerSchedulerDao.getCoddlers(machineId);
		//
	}
	
	public List<String> getSites(Long teamId) {
		//
		return coddlerSchedulerDao.getSites(teamId);
		//
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean saveCoddlers(List<Coddler> coddlers) {
		return coddlerSchedulerDao.saveCoddlers(coddlers);
	}
	
	public Configuration getConfiguration(Integer machineId, Long teamId) {
		//
		return teamId != null ? coddlerSchedulerDao.getConfiguration(machineId, teamId) : coddlerSchedulerDao.getConfiguration(machineId);
		//
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void saveConfiguration(Configuration configuration) {
		coddlerSchedulerDao.saveConfiguration(configuration);
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Integer saveMachineId(String machineId) throws Exception {
		return coddlerSchedulerDao.saveMachineId(machineId);
	}
}
