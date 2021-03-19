package com.jobdiva.api.dao.candidate;

import com.jobdiva.api.model.Candidate;
import com.jobdiva.api.model.webhook.WebhookCandidate;

/**
 * @author Joseph Chidiac
 *
 */
public class WebhookCandidateRowMapper extends CandidateRowMapper {
	
	@Override
	protected Candidate createCandidate() {
		WebhookCandidate candidate = new WebhookCandidate();
		return candidate;
	}
}
