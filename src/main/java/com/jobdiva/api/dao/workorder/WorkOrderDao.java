package com.jobdiva.api.dao.workorder;

import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.workorder.WorkOrder;

/**
 * @author Joseph Chidiac
 *
 */
@Component
public class WorkOrderDao extends AbstractJobDivaDao {
	
	public WorkOrder getWorkOrder(JobDivaSession jobDivaSession, Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
