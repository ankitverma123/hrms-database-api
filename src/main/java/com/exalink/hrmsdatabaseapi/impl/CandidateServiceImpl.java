package com.exalink.hrmsdatabaseapi.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.entity.candidate.Candidate;
import com.exalink.hrmsdatabaseapi.entity.candidate.CandidateSources;
import com.exalink.hrmsdatabaseapi.entity.candidate.FinancialYear;
import com.exalink.hrmsdatabaseapi.entity.candidate.OnboardStatus;
import com.exalink.hrmsdatabaseapi.entity.competency.SubCompetency;
import com.exalink.hrmsdatabaseapi.entity.market.SubBusinessLine;
import com.exalink.hrmsdatabaseapi.repository.ICandidateRepository;
import com.exalink.hrmsdatabaseapi.repository.ICandidateSourceRepository;
import com.exalink.hrmsdatabaseapi.repository.IFinancialYearRepository;
import com.exalink.hrmsdatabaseapi.repository.IMarketOfferingBusinessLineRepository;
import com.exalink.hrmsdatabaseapi.repository.IOnBoardRepository;
import com.exalink.hrmsdatabaseapi.repository.ISubCompetencyRepository;
import com.exalink.hrmsdatabaseapi.service.ICandidateService;

/**
 * @author ankitkverma
 *
 */
@Service
public class CandidateServiceImpl implements ICandidateService {

	private static final String FIRST_NAME = "firstName";
	private static final String MIDDLE_NAME = "middleName";
	private static final String LAST_NAME = "lastName";
	private static final String PRIMARY_CONTACT = "primaryContact";
	private static final String SECONDARY_CONTACT = "secondaryContact";
	private static final String EMAIL_ADDRESS = "emailAddress";
	private static final String GENDER = "gender";
	private static final String TOTAL_EXPERIENCE = "totalExperience";
	private static final String RELEVANT_EXPERIENCE = "relevantExperience";
	private static final String LAST_COMPANY = "lastCompany";
	private static final String CANDIDATE_SOURCEID = "candidateSourceId";
	private static final String ONBOARD_STATUSID = "onboardStatus";
	private static final String FINANCIAL_YEAR = "financialYear";
	private static final String MARKET_BUSINESSLINE = "marketBusinessLine";
	private static final String COMPETENCY = "competency";

	@Autowired
	private ICandidateRepository candidateJPARepository;

	@Autowired
	private ICandidateSourceRepository candidateSourceJPARepository;

	@Autowired
	private IOnBoardRepository onboardJPARepository;

	@Autowired
	private IFinancialYearRepository financialYearRepository;

	@Autowired
	private IMarketOfferingBusinessLineRepository marketOfferingSubBusinessLineRepo;

	@Autowired
	private ISubCompetencyRepository subCompetencyRepository;

	@Override
	public Candidate saveCandidate(Map<String, Object> candidateRequestMap) throws BaseException {

		if (candidateRequestMap != null) {
			Candidate candidateObj = new Candidate();

			/*
			 * Check for name. Some name should be present like firstName, middleName or
			 * lastName It's mandatory
			 */
			boolean someNameExist = checkIfSomeNameIsPresent(candidateRequestMap);
			if (!someNameExist) {
				String[] exception = new String[] {
						"Invalid candidate request, Name is missing. Please specify either firstName, middleName or lastName" };
				throwException(exception);
			} else {
				if ((candidateRequestMap.containsKey(FIRST_NAME) && candidateRequestMap.get(FIRST_NAME) != null))
					candidateObj.setFirstName(candidateRequestMap.get(FIRST_NAME).toString());
				if ((candidateRequestMap.containsKey(MIDDLE_NAME) && candidateRequestMap.get(MIDDLE_NAME) != null))
					candidateObj.setMiddleName(candidateRequestMap.get(MIDDLE_NAME).toString());
				if ((candidateRequestMap.containsKey(LAST_NAME) && candidateRequestMap.get(LAST_NAME) != null))
					candidateObj.setLastName(candidateRequestMap.get(LAST_NAME).toString());
			}

			/*
			 * Check for contact number Some contact number must be passed either
			 * primaryContact number or secondaryContact number One contact number is also
			 * mandatory
			 */
			boolean someContactExist = checkIfSomeContactNumberIsPresent(candidateRequestMap);
			if (!someContactExist) {
				String[] exception = new String[] {
						"Invalid candidate request, Atlease one contact number should be present either primary contact number or seconday contact number" };
				throwException(exception);
			} else {
				if ((candidateRequestMap.containsKey(PRIMARY_CONTACT)
						&& candidateRequestMap.get(PRIMARY_CONTACT) != null))
					candidateObj.setPrimaryContact(candidateRequestMap.get(PRIMARY_CONTACT).toString());
				if ((candidateRequestMap.containsKey(SECONDARY_CONTACT)
						&& candidateRequestMap.get(SECONDARY_CONTACT) != null))
					candidateObj.setSecondaryContact(candidateRequestMap.get(SECONDARY_CONTACT).toString());
			}

			/*
			 * Check for email address This is also mandatory
			 */
			if (candidateRequestMap.containsKey(EMAIL_ADDRESS) && candidateRequestMap.get(EMAIL_ADDRESS) != null) {
				String email = candidateRequestMap.get(EMAIL_ADDRESS).toString();
				if(isEmailValid(email)) {
					boolean candidateExist = candidateJPARepository.existsByEmailAddress(email);
					if (candidateExist) {
						String[] exception = new String[] { "Invalid candidate request, One record already exist with this mail address "+email };
						throwException(exception);
					} else {
						candidateObj.setEmailAddress(email);
					}
				}else {
					String[] exception = new String[] { "Invalid candidate request, Email Address is not valid" };
					throwException(exception);
				}
			}

			if (candidateRequestMap.containsKey(GENDER) && candidateRequestMap.get(GENDER) != null) {
				candidateObj.setGender(candidateRequestMap.get(GENDER).toString());
			} else {
				String[] exception = new String[] { "Invalid candidate request, Please specify gender" };
				throwException(exception);
			}

			if (candidateRequestMap.containsKey(TOTAL_EXPERIENCE)
					&& candidateRequestMap.get(TOTAL_EXPERIENCE) != null) {
				candidateObj.setTotalExperience(Double.valueOf(candidateRequestMap.get(TOTAL_EXPERIENCE).toString()));
			}

			if (candidateRequestMap.containsKey(RELEVANT_EXPERIENCE)
					&& candidateRequestMap.get(RELEVANT_EXPERIENCE) != null) {
				candidateObj.setRelevantExperience(Double.valueOf(candidateRequestMap.get(RELEVANT_EXPERIENCE).toString()));
			}

			if (candidateRequestMap.containsKey(LAST_COMPANY) && candidateRequestMap.get(LAST_COMPANY) != null) {
				candidateObj.setLastCompany((String) candidateRequestMap.get(LAST_COMPANY));
			}

			if (candidateRequestMap.containsKey(CANDIDATE_SOURCEID)
					&& candidateRequestMap.get(CANDIDATE_SOURCEID) != null) {
				Long candidateSourceId = Long.valueOf(candidateRequestMap.get(CANDIDATE_SOURCEID).toString());
				Optional<CandidateSources> cs = candidateSourceJPARepository
						.findById(candidateSourceId);
				if (cs.isPresent()) {
					candidateObj.setSource(cs.get());
				}else {
					String[] exception = new String[] { "Invalid candidate request, Invalid candidateSourceId passed" };
					throwException(exception);
				}
			}

			if (candidateRequestMap.containsKey(ONBOARD_STATUSID)
					&& candidateRequestMap.get(ONBOARD_STATUSID) != null) {
				Long onboardStatusId= Long.valueOf(candidateRequestMap.get(ONBOARD_STATUSID).toString());
				Optional<OnboardStatus> cs = onboardJPARepository
						.findById(onboardStatusId);
				if (cs.isPresent()) {
					candidateObj.setOnboardStatus(cs.get());
				} else {
					String[] exception = new String[] { "Invalid candidate request, Invalid Onboard Status passed" };
					throwException(exception);
				}
			}

			if (candidateRequestMap.containsKey(FINANCIAL_YEAR) && candidateRequestMap.get(FINANCIAL_YEAR) != null) {
				Long financialYearId = Long.valueOf(candidateRequestMap.get(FINANCIAL_YEAR).toString());
				Optional<FinancialYear> cs = financialYearRepository
						.findById(financialYearId);
				if (cs.isPresent()) {
					candidateObj.setFinancialYear(cs.get());
				} else {
					String[] exception = new String[] { "Invalid candidate request, Invalid fianancial year passed" };
					throwException(exception);
				}
			}

			if (candidateRequestMap.containsKey(MARKET_BUSINESSLINE)
					&& candidateRequestMap.get(MARKET_BUSINESSLINE) != null) {
				Long subBusinessLine= Long.valueOf(candidateRequestMap.get(MARKET_BUSINESSLINE).toString());
				Optional<SubBusinessLine> cs = marketOfferingSubBusinessLineRepo
						.findById(subBusinessLine);
				if (cs.isPresent()) {
					candidateObj.setMarketBusinessLine(cs.get());
				} else {
					String[] exception = new String[] {
							"Invalid candidate request, Invalid market offering or sub business line passed" };
					throwException(exception);
				}
			} else {
				String[] exception = new String[] {
						"Invalid candidate request, Market offering or sub business line missing from request" };
				throwException(exception);
			}

			if (candidateRequestMap.containsKey(COMPETENCY) && candidateRequestMap.get(COMPETENCY) != null) {
				Long competencyId= Long.valueOf(candidateRequestMap.get(COMPETENCY).toString());
				Optional<SubCompetency> cs = subCompetencyRepository
						.findById(competencyId);
				if (cs.isPresent()) {
					candidateObj.setCompetency(cs.get());
				} else {
					String[] exception = new String[] { "Invalid candidate request, Invalid competency passed" };
					throwException(exception);
				}
			} else {
				String[] exception = new String[] { "Invalid candidate request, competency missing from request" };
				throwException(exception);
			}

			return candidateJPARepository.save(candidateObj);
		} else {
			String[] exception = new String[] { "Invalid candidate request, request body can't be empty or null" };
			throwException(exception);
		}

		return null;
	}

	@Override
	public Candidate update(Map<String, Object> candidateRequestMap) {
		return null;
	}

	private void throwException(String exception[]) throws BaseException {
		throw new BaseException(CandidateServiceImpl.class, exception);
	}

	private boolean checkIfSomeNameIsPresent(Map<String, Object> request) {
		if((request.containsKey(FIRST_NAME) && request.get(FIRST_NAME) != null)
				|| (request.containsKey(MIDDLE_NAME) && request.get(MIDDLE_NAME) != null)
				|| (request.containsKey(LAST_NAME) && request.get(LAST_NAME) != null)) {
			return true;
		}
		return false;
	}

	private boolean checkIfSomeContactNumberIsPresent(Map<String, Object> request) {
		if((request.containsKey(PRIMARY_CONTACT) && request.get(PRIMARY_CONTACT) != null)
				|| (request.containsKey(SECONDARY_CONTACT) && request.get(SECONDARY_CONTACT) != null)) {
			return true;
		}
		return false;
	}

	private boolean isEmailValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}
}
