package com.exalink.hrmsdatabaseapi.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.exalink.hrmsdatabaseapi.entity.Report;
import com.exalink.hrmsdatabaseapi.entity.candidate.Candidate;
import com.exalink.hrmsdatabaseapi.entity.candidate.CandidateSources;
import com.exalink.hrmsdatabaseapi.entity.candidate.FinancialYear;
import com.exalink.hrmsdatabaseapi.entity.candidate.OnboardStatus;
import com.exalink.hrmsdatabaseapi.entity.competency.Competency;
import com.exalink.hrmsdatabaseapi.entity.competency.SubCompetency;
import com.exalink.hrmsdatabaseapi.entity.market.MarketOffering;
import com.exalink.hrmsdatabaseapi.entity.market.SubBusinessLine;
import com.exalink.hrmsdatabaseapi.entity.offer.Offer;
import com.exalink.hrmsdatabaseapi.entity.offer.OfferDeclineCategory;
import com.exalink.hrmsdatabaseapi.entity.offer.OfferStatus;
import com.exalink.hrmsdatabaseapi.repository.ICandidateRepository;
import com.exalink.hrmsdatabaseapi.repository.ICandidateSourceRepository;
import com.exalink.hrmsdatabaseapi.repository.ICompetencyRepository;
import com.exalink.hrmsdatabaseapi.repository.IFinancialYearRepository;
import com.exalink.hrmsdatabaseapi.repository.IMarketOfferingBusinessLineRepository;
import com.exalink.hrmsdatabaseapi.repository.IMarketOfferingRepository;
import com.exalink.hrmsdatabaseapi.repository.IOfferDeclineRepository;
import com.exalink.hrmsdatabaseapi.repository.IOfferStatusRepository;
import com.exalink.hrmsdatabaseapi.repository.IOnBoardRepository;
import com.exalink.hrmsdatabaseapi.repository.IReportRepository;
import com.exalink.hrmsdatabaseapi.repository.ISubCompetencyRepository;
import com.exalink.hrmsdatabaseapi.service.ICandidateService;
import com.exalink.hrmsdatabaseapi.utils.FiltersPredicateUtil;
import com.exalink.hrmsfabric.common.BaseException;
import com.exalink.hrmsfabric.common.CommonConstants;
import com.exalink.hrmsfabric.common.Utils;
import com.exalink.hrmsfabric.utils.CSVUtils;
import com.exalink.hrmsfabric.utils.LoggingUtil;

/**
 * @author ankitkverma
 *
 */
@Service
public class CandidateServiceImpl implements ICandidateService {

	private static final String CLASSNAME = CandidateServiceImpl.class.getName();
	private static final String ID = "id";
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

	@Autowired
	private IOfferStatusRepository offerStatusRepository;

	@Autowired
	private IOfferDeclineRepository offerDeclineRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private IReportRepository reportRepo;

	@Autowired
	private IMarketOfferingRepository marketOfferingRepo;

	@Autowired
	private ICompetencyRepository competencyRepo;

	@Override
	public Candidate saveCandidate(Map<String, Object> candidateRequestMap) throws BaseException {
		LoggingUtil.debug(CLASSNAME, "saveCandidate() >> START");
		if (candidateRequestMap != null && !candidateRequestMap.isEmpty() && !Utils.checkCollectionHasKeyAndValue(candidateRequestMap, ID)) {
			LoggingUtil.info(CLASSNAME, "saveCandidate() >> New Candidate Request");
			Candidate candidateObj = new Candidate();
			candidateEntityObjectPopulator(candidateRequestMap, candidateObj, null);
			candidateObj.setCreatedAt(new Date());
			return candidateJPARepository.save(candidateObj);
		} else if (candidateRequestMap != null && !candidateRequestMap.isEmpty() && Utils.checkCollectionHasKeyAndValue(candidateRequestMap, ID)) {
			LoggingUtil.info(CLASSNAME, "saveCandidate() >> Modify Candidate Request");
			updateCandidate(candidateRequestMap);
		}
		String[] exception = new String[] { "Invalid candidate request, request body can't be empty or null" };
		throwException(exception);
		return null;
	}

	@Override
	public Candidate updateCandidate(Map<String, Object> candidateRequestMap) throws BaseException {
		LoggingUtil.debug(CLASSNAME, "updateCandidate() >> START");
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, ID)) {
			UUID candidateId = UUID.fromString(candidateRequestMap.get(ID).toString());
			Optional<Candidate> existingCandidate = candidateJPARepository.findById(candidateId);
			if (existingCandidate.isPresent()) {
				Candidate candidateObj = existingCandidate.get();
				candidateEntityObjectPopulator(candidateRequestMap, candidateObj, candidateId);
				candidateObj.setUpdatedAt(new Date());
				return candidateJPARepository.save(candidateObj);
			} else {
				LoggingUtil.error(CLASSNAME, "updateCandidate() >> Invalid candidate request, Invalid Candidate Id");
				String[] exception = new String[] { "Invalid candidate request, Invalid Candidate Id" };
				throwException(exception);
			}
		} else {
			LoggingUtil.error(CLASSNAME, "updateCandidate() >> Invalid candidate request, request body can't be empty or null");
			String[] exception = new String[] { "Invalid candidate request, request body can't be empty or null" };
			throwException(exception);
		}
		return null;
	}
	
	@Override
	public List<String> saveCandidate(List<Map<String, Object>> candidateRequestMap) throws BaseException {
		List<String> cs = new ArrayList<>();
		candidateRequestMap.forEach(map -> {
			try {
				Candidate c = saveCandidate(map);
				cs.add(c.getFirstName() + " Created Succfully");
			} catch (BaseException e) {
				LoggingUtil.error(CandidateServiceImpl.class.getName(),
						"Exception saveCandidate() >> " + e.getMessage());
			}
		});
		return cs;
	}

	@Override
	public Map<String, Object> listCandidates(Integer pageNumber, Integer pageSize, String sortField, String sortDirection,
			String filter) throws BaseException {
		List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Candidate> query = builder.createQuery(Candidate.class);
		Root<Candidate> r = query.from(Candidate.class);
		List<Predicate> predicatesGenerated = FiltersPredicateUtil.generatePredicatesFilters(builder, r, filter);

		if (predicatesGenerated != null && !predicatesGenerated.isEmpty())
			predicates.addAll(predicatesGenerated);

		query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

		if (sortField != null && sortDirection != null) {
			if (sortDirection.equals(CommonConstants.SORT_DIRECTION_ASC)) {
				query.orderBy(builder.asc(r.get(sortField)));
			} else if (sortDirection.equals(CommonConstants.SORT_DIRECTION_DESC)) {
				query.orderBy(builder.desc(r.get(sortField)));
			}
		} else {
			query.orderBy(builder.desc(r.get(CommonConstants.ID)));
		}
		
		List<Candidate> result = entityManager.createQuery(query).setFirstResult((pageNumber-1) * pageSize).setMaxResults(pageSize)
				.getResultList();
		for (Candidate candidate : result) {
			candidate.setFullName((candidate.getFirstName() == null ? "" : candidate.getFirstName())
					.concat(candidate.getMiddleName() == null ? " " : " " + candidate.getMiddleName())
					.concat(candidate.getLastName() == null ? " " : " " + candidate.getLastName()));
		}
		int totalCount = entityManager.createQuery(query).getResultList().size();

		Map<String, Object> dataToBeReturned = new HashMap<>();
		dataToBeReturned.put(CommonConstants.RESULTS, result);
		dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);
		dataToBeReturned.put(CommonConstants.LAST_PAGE, (int)((totalCount/pageSize)+1));

		return dataToBeReturned;
	}

	@Override
	@Transactional
	public Object saveCandidate(MultipartFile file) throws IOException, BaseException {
		List<String[]> allData = CSVUtils.readCSVFileContents(file);

		Optional<Report> reportTemplate = reportRepo.findByReportName(CommonConstants.CANDIDATE_REPORT_TEMPLATE);
		if (!reportTemplate.isPresent()) {
			throw new IOException("No Report Template Found");
		}

		String[] headers = reportTemplate.get().getKeys().split(",");

		String[] headerRow = allData.get(0);
		int col = 0;
		Boolean headersMisMatch = false;
		for (String string : headerRow) {
			if (!string.equals(headers[col])) {
				headersMisMatch = true;
				break;
			}
			col++;
		}

		if (headersMisMatch) {
			throw new BaseException(CandidateServiceImpl.class, "CSV Headers Mismatch! Expected Headers are " + headerRow);
		}

		allData.remove(0);

		int completeData = allData.size();
		for (int i = 0; i < completeData; i++) {
			Map<String, Object> candidateMap = populateCandidateMap(allData.get(i), headers);
			if (candidateMap != null) {
				saveCandidate(candidateMap);
			}
		}

		return "Candidate saved successfully";
	}

	private void throwException(String[] exception) throws BaseException {
		throw new BaseException(CandidateServiceImpl.class, exception);
	}

	protected boolean checkIfSomeNameIsPresent(Map<String, Object> request) {
		return ((request.containsKey(FIRST_NAME) && request.get(FIRST_NAME) != null)
				|| (request.containsKey(MIDDLE_NAME) && request.get(MIDDLE_NAME) != null)
				|| (request.containsKey(LAST_NAME) && request.get(LAST_NAME) != null));
	}

	protected boolean checkIfSomeContactNumberIsPresent(Map<String, Object> request) {
		return ((request.containsKey(PRIMARY_CONTACT) && request.get(PRIMARY_CONTACT) != null)
				|| (request.containsKey(SECONDARY_CONTACT) && request.get(SECONDARY_CONTACT) != null));
	}

	@SuppressWarnings("unused")
	private boolean isEmailValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}
	
	protected void candidateEntityObjectPopulator(Map<String, Object> candidateRequestMap, Candidate candidateObj, UUID candidateId) throws BaseException {
		LoggingUtil.debug(CLASSNAME, "candidateEntityObjectPopulator() >> START");
		nameMapper(candidateRequestMap, candidateObj, candidateId == null ? Boolean.FALSE : Boolean.TRUE);
		genderMapper(candidateRequestMap, candidateObj, candidateId == null ? Boolean.FALSE : Boolean.TRUE);
		contactMapper(candidateRequestMap, candidateObj, candidateId);
		emailAddressMapper(candidateRequestMap, candidateObj, candidateId);

		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, TOTAL_EXPERIENCE))
			candidateObj.setTotalExperience(Double.valueOf(candidateRequestMap.get(TOTAL_EXPERIENCE).toString()));

		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, RELEVANT_EXPERIENCE))
			candidateObj.setRelevantExperience(Double.valueOf(candidateRequestMap.get(RELEVANT_EXPERIENCE).toString()));

		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, LAST_COMPANY))
			candidateObj.setLastCompany((String) candidateRequestMap.get(LAST_COMPANY));

		candidateResourceMapper(candidateRequestMap, candidateObj);
		onBoardStatusMapper(candidateRequestMap, candidateObj);
		financialYearMapper(candidateRequestMap, candidateObj);
		marketOfferingMapper(candidateRequestMap, candidateObj);
		marketSubBusinessLineMapper(candidateRequestMap, candidateObj);
		competencyMapper(candidateRequestMap, candidateObj);
		subCompetencyMapper(candidateRequestMap, candidateObj);
		candidateOfferStatusMapper(candidateRequestMap, candidateObj);
	}
	

	protected void candidateOfferStatusMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		LoggingUtil.debug(CLASSNAME, "candidateOfferStatusMapper() >> START");
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.OFFERSTATUS_STATUS)) {
			String offferStatusUUIDStr = candidateRequestMap.get(CommonConstants.OFFERSTATUS_STATUS).toString();
			if (!Utils.checkIfUUID(offferStatusUUIDStr))
				throw new BaseException(CandidateServiceImpl.class,
						String.format(CommonConstants.INVALID_UUID_ERROR_MESSAGE, CommonConstants.OFFERSTATUS_STATUS));
			UUID offerStatusId = UUID.fromString(offferStatusUUIDStr);
			Optional<OfferStatus> persistedObject = offerStatusRepository.findById(offerStatusId);
			if (persistedObject.isPresent()) {
				OfferStatus os = persistedObject.get();

				Offer offer = new Offer();
				offer.setStatus(os);

				if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.OFFERSTATUS_MESSAGE))
					offer.setComment(candidateRequestMap.get(CommonConstants.OFFERSTATUS_MESSAGE).toString());

				if (os.getStatus().equals(CommonConstants.DECLINED))
					offerDeclineCategoryMapper(candidateRequestMap, offer);
				
				candidateObj.setCandidateOfferStatus(offer);
			} else {
				throw new BaseException(CandidateServiceImpl.class, "Invalid offer status passed");
			}
		}
	}
	
	protected void offerDeclineCategoryMapper(Map<String, Object> candidateRequestMap, Offer offer) throws BaseException{
		if(Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.OFFERSTATUS_CATEGORY)) {
			String offerDeclineCategoryId = candidateRequestMap.get(CommonConstants.OFFERSTATUS_CATEGORY).toString();
			if(!Utils.checkIfUUID(offerDeclineCategoryId))
				throw new BaseException(CandidateServiceImpl.class, String.format(CommonConstants.INVALID_UUID_ERROR_MESSAGE, CommonConstants.OFFERSTATUS_CATEGORY));
			Optional<OfferDeclineCategory> odc = offerDeclineRepository.findById(UUID.fromString(offerDeclineCategoryId));
			if(odc.isPresent()) {
				offer.setDeclineCategory(odc.get());
			}else {
				throw new BaseException(CandidateServiceImpl.class, "Invalid offer decline category specified");
			}
		}else {
			throw new BaseException(CandidateServiceImpl.class, "Please specify offer decline category");
		}
	}
	
	private void nameMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj, boolean modifyScenario) throws BaseException {
		if(!modifyScenario && !checkIfSomeNameIsPresent(candidateRequestMap)) {
			throw new BaseException(CandidateServiceImpl.class, "Invalid candidate request, Name is missing. Please specify either firstName, middleName or lastName");
		}
		
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, FIRST_NAME))
			candidateObj.setFirstName(candidateRequestMap.get(FIRST_NAME).toString());
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, MIDDLE_NAME))
			candidateObj.setMiddleName(candidateRequestMap.get(MIDDLE_NAME).toString());
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, LAST_NAME))
			candidateObj.setLastName(candidateRequestMap.get(LAST_NAME).toString());
	}
	
	private void genderMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj, Boolean modifyScenario) throws BaseException {
		if(!modifyScenario && !Utils.checkCollectionHasKeyAndValue(candidateRequestMap, GENDER)) {
			String[] exception = new String[] { "Invalid candidate request, Please specify gender" };
			throwException(exception);
		}
		if(Utils.checkCollectionHasKeyAndValue(candidateRequestMap, GENDER))
			candidateObj.setGender(candidateRequestMap.get(GENDER).toString());
	}

	private void contactMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj, UUID candidateId)
			throws BaseException {
		if (candidateId == null && !checkIfSomeContactNumberIsPresent(candidateRequestMap)) {
			String[] exception = new String[] {
			"Invalid candidate request, Atlease one contact number should be present either primary contact number or seconday contact number" };
			throwException(exception);
		}
		
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, PRIMARY_CONTACT)) {
			String primaryContact = candidateRequestMap.get(PRIMARY_CONTACT).toString();
			primaryContactMapper(primaryContact, candidateId, candidateObj);
		}

		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, SECONDARY_CONTACT)) {
			String secondaryContact = candidateRequestMap.get(SECONDARY_CONTACT).toString();
			secondaryContactMapper(secondaryContact, candidateId, candidateObj);
		}
	}

	private void primaryContactMapper(String contactNumber, UUID candidateId, Candidate candidateObj)
			throws BaseException {
		if (candidateId != null
				&& candidateJPARepository.existsByPrimaryContactForSomeOtherUser(contactNumber, candidateId)) {
			throw new BaseException(CandidateServiceImpl.class,
					String.format("%s is already bring used for some other candidate", contactNumber));
		} else if (candidateId == null
				&& candidateJPARepository.existsByPrimaryOrSecondaryContact(contactNumber, contactNumber)) {
			throw new BaseException(CandidateServiceImpl.class, String.format(
					"%s is already bring used for some other candidate either as a primary contact or secondary contact",
					contactNumber));
		}
		candidateObj.setPrimaryContact(contactNumber);
	}

	protected void secondaryContactMapper(String contactNumber, UUID candidateId, Candidate candidateObj)
			throws BaseException {
		if (candidateId != null
				&& candidateJPARepository.existsByPrimaryContactForSomeOtherUser(contactNumber, candidateId)) {
			throw new BaseException(CandidateServiceImpl.class,
					String.format("%s is already bring used for some other candidate", contactNumber));
		} else if (candidateId == null
				&& candidateJPARepository.existsByPrimaryOrSecondaryContact(contactNumber, contactNumber)) {
			throw new BaseException(CandidateServiceImpl.class, String.format(
					"%s is already bring used for some other candidate either as a primary contact or secondary contact",
					contactNumber));
		}
		candidateObj.setPrimaryContact(contactNumber);
	}

	private void emailAddressMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj, UUID candidateId)
			throws BaseException {
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, EMAIL_ADDRESS)) {
			String emailAddress = candidateRequestMap.get(EMAIL_ADDRESS).toString();
			if (candidateId != null
					&& candidateJPARepository.existsByEmailAddressForSomeOtherUser(emailAddress, candidateId))
				throw new BaseException(CandidateServiceImpl.class,
						String.format("%s is already bein used for some other user", emailAddress));
			else if (candidateId == null && candidateJPARepository.existsByEmailAddress(emailAddress))
				throw new BaseException(CandidateServiceImpl.class,
						String.format("%s is already bein used by some other user", emailAddress));

			candidateObj.setEmailAddress(emailAddress);
		}
	}

	private void candidateResourceMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.CSV_CANDIDATE_SOURCE_KEY)) {
			Object objectValueHolder = candidateRequestMap.get(CommonConstants.CSV_CANDIDATE_SOURCE_KEY);
			if (objectValueHolder instanceof CandidateSources) {
				CandidateSources source = (CandidateSources) objectValueHolder;
				candidateObj.setCandidateSource(source);
			} else {
				UUID candidateSourceUUID = UUID
						.fromString(candidateRequestMap.get(CommonConstants.CSV_CANDIDATE_SOURCE_KEY).toString());
				Optional<CandidateSources> candidateSource = candidateSourceJPARepository.findById(candidateSourceUUID);
				if (candidateSource.isPresent()) {
					candidateObj.setCandidateSource(candidateSource.get());
				} else {
					String[] exception = new String[] { "Invalid candidate request, Invalid candidateSourceId passed" };
					throwException(exception);
				}
			}
		}
	}

	private void onBoardStatusMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.CSV_ONBOARD_STATUS_KEY)) {
			Object objectValueHolder = candidateRequestMap.get(CommonConstants.CSV_ONBOARD_STATUS_KEY);
			if (objectValueHolder instanceof OnboardStatus) {
				OnboardStatus entityObj = (OnboardStatus) objectValueHolder;
				candidateObj.setOnboardStatus(entityObj);
			} else {
				UUID onboardStatusUUID = UUID
						.fromString(candidateRequestMap.get(CommonConstants.CSV_ONBOARD_STATUS_KEY).toString());
				Optional<OnboardStatus> onboardStatus = onboardJPARepository.findById(onboardStatusUUID);
				if (onboardStatus.isPresent()) {
					candidateObj.setOnboardStatus(onboardStatus.get());
				} else {
					String[] exception = new String[] { "Invalid candidate request, Invalid Onboard Status passed" };
					throwException(exception);
				}
			}
		}
	}

	private void financialYearMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.CSV_FINANCIAL_YEAR_KEY)) {
			Object objectValueHolder = candidateRequestMap.get(CommonConstants.CSV_FINANCIAL_YEAR_KEY);
			if (objectValueHolder instanceof FinancialYear) {
				FinancialYear entityObj = (FinancialYear) objectValueHolder;
				candidateObj.setFinancialYear(entityObj);
			} else {
				UUID financialYearUUID = UUID
						.fromString(candidateRequestMap.get(CommonConstants.CSV_FINANCIAL_YEAR_KEY).toString());
				Optional<FinancialYear> financialYear = financialYearRepository.findById(financialYearUUID);
				if (financialYear.isPresent()) {
					candidateObj.setFinancialYear(financialYear.get());
				} else {
					String[] exception = new String[] { "Invalid candidate request, Invalid fianancial year passed" };
					throwException(exception);
				}
			}
		}
	}

	private void marketOfferingMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.CSV_MARKET_OFFERING_KEY)) {
			Object objectValueHolder = candidateRequestMap.get(CommonConstants.CSV_MARKET_OFFERING_KEY);
			if (objectValueHolder instanceof MarketOffering) {
				MarketOffering entityObj = (MarketOffering) objectValueHolder;
				candidateObj.setMarketOffering(entityObj);
			} else {
				UUID uuid = UUID
						.fromString(candidateRequestMap.get(CommonConstants.CSV_MARKET_OFFERING_KEY).toString());
				Optional<MarketOffering> entityObj = marketOfferingRepo.findById(uuid);
				if (entityObj.isPresent()) {
					candidateObj.setMarketOffering(entityObj.get());
				} else {
					String[] exception = new String[] {
							"Invalid candidate request, Invalid market offering value specified" };
					throwException(exception);
				}
			}
		}
	}

	private void competencyMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.CSV_COMPETENCY_KEY)) {
			Object objectValueHolder = candidateRequestMap.get(CommonConstants.CSV_COMPETENCY_KEY);
			if (objectValueHolder instanceof Competency) {
				Competency entityObj = (Competency) objectValueHolder;
				candidateObj.setCompetency(entityObj);
			} else {
				UUID uuid = UUID.fromString(candidateRequestMap.get(CommonConstants.CSV_COMPETENCY_KEY).toString());
				Optional<Competency> entityObj = competencyRepo.findById(uuid);
				if (entityObj.isPresent()) {
					candidateObj.setCompetency(entityObj.get());
				} else {
					String[] exception = new String[] { "Invalid candidate request, Invalid competency value passed" };
					throwException(exception);
				}
			}
		}
	}

	private void marketSubBusinessLineMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap,
				CommonConstants.CSV_MARKET_SUB_BUSINESS_LINE_KEY)) {
			Object objectValueHolder = candidateRequestMap.get(CommonConstants.CSV_MARKET_SUB_BUSINESS_LINE_KEY);
			if (objectValueHolder instanceof SubBusinessLine) {
				SubBusinessLine entityObj = (SubBusinessLine) objectValueHolder;
				candidateObj.setSubBusinessLine(entityObj);
			} else {
				UUID subBusinessLineUUID = UUID.fromString(
						candidateRequestMap.get(CommonConstants.CSV_MARKET_SUB_BUSINESS_LINE_KEY).toString());
				Optional<SubBusinessLine> subBusinessLine = marketOfferingSubBusinessLineRepo
						.findById(subBusinessLineUUID);
				if (subBusinessLine.isPresent()) {
					candidateObj.setSubBusinessLine(subBusinessLine.get());
				} else {
					String[] exception = new String[] {
							"Invalid candidate request, Invalid market offering or sub business line passed" };
					throwException(exception);
				}
			}
		}
	}
	
	private void subCompetencyMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.CSV_SUB_COMPETENCY_KEY)) {
			Object objectValueHolder = candidateRequestMap.get(CommonConstants.CSV_SUB_COMPETENCY_KEY);
			if (objectValueHolder instanceof SubCompetency) {
				SubCompetency entityObj = (SubCompetency) objectValueHolder;
				candidateObj.setSubCompetency(entityObj);
			} else {
				UUID subCompetencyUUID = UUID
						.fromString(candidateRequestMap.get(CommonConstants.CSV_SUB_COMPETENCY_KEY).toString());
				Optional<SubCompetency> subCompetency = subCompetencyRepository.findById(subCompetencyUUID);
				if (subCompetency.isPresent()) {
					candidateObj.setSubCompetency(subCompetency.get());
				} else {
					String[] exception = new String[] { "Invalid candidate request, Invalid competency passed" };
					throwException(exception);
				}
			}
		}
	}
	
	private Map<String, Object> populateCandidateMap(String[] rowData, String headers[]) {
		int totalHeader = headers.length;
		if (totalHeader == rowData.length) {
			Map<String, Object> candidateMap = new HashMap<>();
			for (int i = 0; i < totalHeader; i++) {
				String header = headers[i];
				String value = rowData[i];
				switch (header) {
				case CommonConstants.CSV_CANDIDATE_SOURCE_KEY:
					Optional<CandidateSources> candidateSource = Utils.checkIfUUID(value)
							? candidateSourceJPARepository.findById(UUID.fromString(value))
							: candidateSourceJPARepository.findByCandidateSource(value);
					if (candidateSource.isPresent()) {
						candidateMap.put(header, candidateSource.get());
					}
					break;
				case CommonConstants.CSV_ONBOARD_STATUS_KEY:
					Optional<OnboardStatus> onboardStatus = Utils.checkIfUUID(value)
							? onboardJPARepository.findById(UUID.fromString(value))
							: onboardJPARepository.findByOnboardStatus(value);
					if (onboardStatus.isPresent()) {
						candidateMap.put(header, onboardStatus.get());
					}
					break;
				case CommonConstants.CSV_FINANCIAL_YEAR_KEY:
					Optional<FinancialYear> financialYear = Utils.checkIfUUID(value)
							? financialYearRepository.findById(UUID.fromString(value))
							: financialYearRepository.findByFinancialYear(value);
					if (financialYear.isPresent()) {
						candidateMap.put(header, financialYear.get());
					}
					break;
				case CommonConstants.CSV_MARKET_OFFERING_KEY:
					Optional<MarketOffering> marketOffering = Utils.checkIfUUID(value)
							? marketOfferingRepo.findById(UUID.fromString(value))
							: marketOfferingRepo.findByMarket(value);
					if (marketOffering.isPresent()) {
						candidateMap.put(header, marketOffering.get());
					}
					break;
				case CommonConstants.CSV_MARKET_SUB_BUSINESS_LINE_KEY:
					Object marketOfferingObject = candidateMap.get(CommonConstants.CSV_MARKET_OFFERING_KEY);
					String marketOfferingName = null;
					if (marketOfferingObject instanceof String)
						marketOfferingName = (String) marketOfferingObject;
					else
						marketOfferingName = ((MarketOffering) marketOfferingObject).getMarket();
					Optional<SubBusinessLine> subBusinessLine = Utils.checkIfUUID(value)
							? marketOfferingSubBusinessLineRepo.findById(UUID.fromString(value))
							: marketOfferingSubBusinessLineRepo.findBySubBusinessLineAndMarketOffering(value,
									marketOfferingName);
					if (subBusinessLine.isPresent()) {
						candidateMap.put(header, subBusinessLine.get());
					}
					break;
				case CommonConstants.CSV_COMPETENCY_KEY:
					Optional<Competency> competency = Utils.checkIfUUID(value)
							? competencyRepo.findById(UUID.fromString(value))
							: competencyRepo.findByCompetency(value);
					if (competency.isPresent()) {
						candidateMap.put(header, competency.get());
					}
					break;
				case CommonConstants.CSV_SUB_COMPETENCY_KEY:
					Object competencyObject = candidateMap.get(CommonConstants.CSV_COMPETENCY_KEY);
					String competencyName = null;
					if (competencyObject instanceof String)
						competencyName = (String) competencyObject;
					else
						competencyName = ((Competency) competencyObject).getCompetency();
					Optional<SubCompetency> subCompetency = Utils.checkIfUUID(value)
							? subCompetencyRepository.findById(UUID.fromString(value))
							: subCompetencyRepository.findBySubCompetencyAndCompetency(value, competencyName);
					if (subCompetency.isPresent()) {
						candidateMap.put(header, subCompetency.get());
					}
					break;

				default:
					candidateMap.put(header, value);
					break;
				}
			}
			return candidateMap;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.exalink.hrmsdatabaseapi.service.ICandidateService#candidateOfferStatus(java.util.UUID)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object candidateOfferStatus(UUID candidateId) throws BaseException {
		/*
		 * This code needs to be reformatted, want to use left outer join so that even if the values 
		 * are not present in Offer entity then it should return null in those cases.
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT new map(");
		sb.append("c.id as id, ");
		sb.append("c.candidateOfferStatus.comment as offerComment, ");
		sb.append("c.candidateOfferStatus.status.id as offerStatusSelected, ");
		sb.append("c.candidateOfferStatus.declineCategory.id as offerDeclineCategory) ");
		sb.append("FROM Candidate c ");
		sb.append("WHERE c.id = '"+candidateId+"'");
		List<Map<String, Object>> resultSet = entityManager.createQuery(sb.toString()).getResultList();
		if(resultSet.isEmpty()) {
			Map<String, Object> tempResponse = new HashMap<>();
			tempResponse.put(CommonConstants.ID, candidateId);
			return tempResponse;
		}
		return resultSet.get(0);
	}
	
	
}
