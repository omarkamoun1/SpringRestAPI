package com.jobdiva.api.dao.onboard;

import com.jobdiva.api.dao.AbstractJobDivaDao;

/**
 * @author Joseph Chidiac
 *
 */
public class AbstractOnBoardDao extends AbstractJobDivaDao {
	
	public final String	WEB_FORM		= "Web Form";
	public final String	URL				= "URL";
	public final String	DESIGN_FORM		= "Design Form";
	//
	public final String	CANDIDATE		= "Candidate";
	public final String	SUPPLIER		= "Supplier";
	public final String	INTERNAL_USE	= "Internal Use";
	
	protected String getStringDocumentType(Integer documetType) {
		if (documetType != null) {
			switch (documetType) {
				case 0:
					return WEB_FORM;
				case 1:
					return URL;
				case 2:
					return DESIGN_FORM;
			}
		}
		return null;
	}
	
	protected int getIntDocumentType(String documetType) {
		if (documetType != null) {
			if (documetType.equalsIgnoreCase(WEB_FORM))
				return 0;
			else if (documetType.equalsIgnoreCase(URL))
				return 1;
			else if (documetType.equalsIgnoreCase(DESIGN_FORM))
				return 2;
		}
		return 0;
	}
	
	// -- 0 Candidate
	// -- 1 Supplier
	// -- 2 internal use
	protected String getStringSendTo(Integer sendTo) {
		if (sendTo != null) {
			switch (sendTo) {
				case 0:
					return CANDIDATE;
				case 1:
					return SUPPLIER;
				case 2:
					return INTERNAL_USE;
			}
		}
		return CANDIDATE;
	}
}
