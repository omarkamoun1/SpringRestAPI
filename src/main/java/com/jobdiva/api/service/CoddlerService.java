package com.jobdiva.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.jobdiva.api.dao.coddler.CoddlerSchedulerDao;
import com.jobdiva.api.model.controller.Coddler;
import com.jobdiva.api.model.controller.Configuration;

@Service
public class CoddlerService extends AbstractService {
	
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
	
	public Boolean saveCoddlers(List<Coddler> coddlers) {
		//
		return inTransaction(new TransactionCallback<Boolean>() {
			
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					//
					return coddlerSchedulerDao.saveCoddlers(coddlers);
					//
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
			//
		});
		//
	}
	
	public Configuration getConfiguration(Integer machineId, Long teamId) {
		//
		return teamId != null ? coddlerSchedulerDao.getConfiguration(machineId, teamId) : coddlerSchedulerDao.getConfiguration(machineId);
		//
	}
	
	public void saveConfiguration(Configuration configuration) {
		inTransaction(new TransactionCallback<Boolean>() {
			
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					//
					coddlerSchedulerDao.saveConfiguration(configuration);
					//
					return true;
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
			//
		});
		//
	}
	
	public Integer saveMachineId(String machineId) throws Exception {
		//
		return inTransaction(new TransactionCallback<Integer>() {
			
			@Override
			public Integer doInTransaction(TransactionStatus status) {
				try {
					//
					return coddlerSchedulerDao.saveMachineId(machineId);
					//
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
			//
		});
		//
	}
}
