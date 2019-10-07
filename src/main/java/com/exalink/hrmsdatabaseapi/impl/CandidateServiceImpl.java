package com.exalink.hrmsdatabaseapi.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.CommonConstants;
import com.exalink.hrmsdatabaseapi.Utils;
import com.exalink.hrmsdatabaseapi.entity.Report;
import com.exalink.hrmsdatabaseapi.entity.candidate.Candidate;
import com.exalink.hrmsdatabaseapi.entity.candidate.CandidateSources;
import com.exalink.hrmsdatabaseapi.entity.candidate.FinancialYear;
import com.exalink.hrmsdatabaseapi.entity.candidate.OnboardStatus;
import com.exalink.hrmsdatabaseapi.entity.competency.SubCompetency;
import com.exalink.hrmsdatabaseapi.entity.market.SubBusinessLine;
import com.exalink.hrmsdatabaseapi.entity.offer.Offer;
import com.exalink.hrmsdatabaseapi.entity.offer.OfferDeclineCategory;
import com.exalink.hrmsdatabaseapi.entity.offer.OfferStatus;
import com.exalink.hrmsdatabaseapi.repository.ICandidateRepository;
import com.exalink.hrmsdatabaseapi.repository.ICandidateSourceRepository;
import com.exalink.hrmsdatabaseapi.repository.IFinancialYearRepository;
import com.exalink.hrmsdatabaseapi.repository.IMarketOfferingBusinessLineRepository;
import com.exalink.hrmsdatabaseapi.repository.IOfferDeclineRepository;
import com.exalink.hrmsdatabaseapi.repository.IOfferStatusRepository;
import com.exalink.hrmsdatabaseapi.repository.IOnBoardRepository;
import com.exalink.hrmsdatabaseapi.repository.IReportRepository;
import com.exalink.hrmsdatabaseapi.repository.ISubCompetencyRepository;
import com.exalink.hrmsdatabaseapi.service.ICandidateService;
import com.exalink.hrmsdatabaseapi.utils.FiltersPredicateUtil;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

/**
 * @author ankitkverma
 *
 */
@Service
public class CandidateServiceImpl implements ICandidateService {

	private static final Logger logger = LogManager.getLogger(CandidateServiceImpl.class);
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
	private static final String CANDIDATE_SOURCEID = "candidateSourceId";
	private static final String ONBOARD_STATUSID = "onboardStatus";
	private static final String FINANCIAL_YEAR = "financialYear";
	private static final String MARKET_BUSINESSLINE = "marketBusinessLine";
	private static final String COMPETENCY = "competency";
	private static final String OFFER_STATUS = "offerStatus";

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

	@Override
	public Candidate saveCandidate(Map<String, Object> candidateRequestMap) throws BaseException {
		logger.debug(CLASSNAME + " >> saveCandidate() >> START");
		if (candidateRequestMap != null && !Utils.checkCollectionHasKeyAndValue(candidateRequestMap, "id")) {
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
				if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, FIRST_NAME))
					candidateObj.setFirstName(candidateRequestMap.get(FIRST_NAME).toString());
				if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, MIDDLE_NAME))
					candidateObj.setMiddleName(candidateRequestMap.get(MIDDLE_NAME).toString());
				if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, LAST_NAME))
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
				if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, PRIMARY_CONTACT))
					candidateObj.setPrimaryContact(candidateRequestMap.get(PRIMARY_CONTACT).toString());
				if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, SECONDARY_CONTACT))
					candidateObj.setSecondaryContact(candidateRequestMap.get(SECONDARY_CONTACT).toString());
			}

			/*
			 * Check for email address This is also mandatory
			 */
			if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, EMAIL_ADDRESS)) {
				String email = candidateRequestMap.get(EMAIL_ADDRESS).toString();
				boolean candidateExist = candidateJPARepository.existsByEmailAddress(email);
				if (candidateExist) {
					String[] exception = new String[] {
							"Invalid candidate request, One record already exist with this mail address " + email };
					throwException(exception);
				} else {
					candidateObj.setEmailAddress(email);
				}
			}

			if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, GENDER)) {
				candidateObj.setGender(candidateRequestMap.get(GENDER).toString());
			} else {
				String[] exception = new String[] { "Invalid candidate request, Please specify gender" };
				throwException(exception);
			}

			if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, TOTAL_EXPERIENCE)) {
				candidateObj.setTotalExperience(Double.valueOf(candidateRequestMap.get(TOTAL_EXPERIENCE).toString()));
			}

			if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, RELEVANT_EXPERIENCE)) {
				candidateObj
						.setRelevantExperience(Double.valueOf(candidateRequestMap.get(RELEVANT_EXPERIENCE).toString()));
			}

			if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, LAST_COMPANY)) {
				candidateObj.setLastCompany((String) candidateRequestMap.get(LAST_COMPANY));
			}

			candidateResourceMapper(candidateRequestMap, candidateObj);
			onBoardStatusMapper(candidateRequestMap, candidateObj);
			financialYearMapper(candidateRequestMap, candidateObj);
			marketSubBusinessLineMapper(candidateRequestMap, candidateObj, false);
			competencyMapper(candidateRequestMap, candidateObj, false);
			candidateObj.setCreatedAt(new Date());
			return candidateJPARepository.save(candidateObj);
		} else {
			if(candidateRequestMap == null) {
			String[] exception = new String[] { "Invalid candidate request, request body can't be empty or null" };
			throwException(exception);
			} else {
				updateCandidate(candidateRequestMap);
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Candidate updateCandidate(Map<String, Object> candidateRequestMap) throws BaseException {
		if (candidateRequestMap != null) {
			if (candidateRequestMap.containsKey(ID) && candidateRequestMap.get(ID) != null) {
				Long candidateId = Long.valueOf(candidateRequestMap.get(ID).toString());
				Optional<Candidate> existingCandidate = candidateJPARepository.findById(candidateId);
				if (existingCandidate.isPresent()) {
					Candidate candidateObj = existingCandidate.get();

					boolean someNameExist = checkIfSomeNameIsPresent(candidateRequestMap);
					if (someNameExist) {
						if ((candidateRequestMap.containsKey(FIRST_NAME)
								&& candidateRequestMap.get(FIRST_NAME) != null))
							candidateObj.setFirstName(candidateRequestMap.get(FIRST_NAME).toString());
						if ((candidateRequestMap.containsKey(MIDDLE_NAME)
								&& candidateRequestMap.get(MIDDLE_NAME) != null))
							candidateObj.setMiddleName(candidateRequestMap.get(MIDDLE_NAME).toString());
						if ((candidateRequestMap.containsKey(LAST_NAME) && candidateRequestMap.get(LAST_NAME) != null))
							candidateObj.setLastName(candidateRequestMap.get(LAST_NAME).toString());
					}

					boolean someContactExist = checkIfSomeContactNumberIsPresent(candidateRequestMap);
					if (someContactExist) {
						if ((candidateRequestMap.containsKey(PRIMARY_CONTACT)
								&& candidateRequestMap.get(PRIMARY_CONTACT) != null)) {
							if (!candidateJPARepository.existsByPrimaryContactForSomeOtherUser(
									candidateRequestMap.get(PRIMARY_CONTACT).toString(), candidateId)) {
								candidateObj.setPrimaryContact(candidateRequestMap.get(PRIMARY_CONTACT).toString());
							} else {
								String[] exception = new String[] { "Invalid candidate request, "
										+ candidateRequestMap.get(PRIMARY_CONTACT) + " is already being used" };
								throwException(exception);
							}
						}
						if ((candidateRequestMap.containsKey(SECONDARY_CONTACT)
								&& candidateRequestMap.get(SECONDARY_CONTACT) != null)) {
							if (!candidateJPARepository.existsBySecondaryContactForSomeOtherUser(SECONDARY_CONTACT,
									candidateId)) {
								candidateObj.setSecondaryContact(candidateRequestMap.get(SECONDARY_CONTACT).toString());
							} else {
								String[] exception = new String[] { "Invalid candidate request, "
										+ candidateRequestMap.get(SECONDARY_CONTACT) + " is already being used" };
								throwException(exception);
							}
						}
					}

					if (candidateRequestMap.containsKey(EMAIL_ADDRESS)
							&& candidateRequestMap.get(EMAIL_ADDRESS) != null) {
						String email = candidateRequestMap.get(EMAIL_ADDRESS).toString();
						boolean someOtherCandidateExistWithRequestedMailAddress = candidateJPARepository
								.existsByEmailAddressForSomeOtherUser(email, candidateId);
						if (someOtherCandidateExistWithRequestedMailAddress) {
							String[] exception = new String[] { "Invalid candidate request, This email address ("
									+ email + ") is already being used" };
							throwException(exception);
						} else {
							candidateObj.setEmailAddress(email);
						}
					}

					if (candidateRequestMap.containsKey(GENDER) && candidateRequestMap.get(GENDER) != null) {
						candidateObj.setGender(candidateRequestMap.get(GENDER).toString());
					}

					if (candidateRequestMap.containsKey(TOTAL_EXPERIENCE)
							&& candidateRequestMap.get(TOTAL_EXPERIENCE) != null) {
						candidateObj.setTotalExperience(
								Double.valueOf(candidateRequestMap.get(TOTAL_EXPERIENCE).toString()));
					}

					if (candidateRequestMap.containsKey(RELEVANT_EXPERIENCE)
							&& candidateRequestMap.get(RELEVANT_EXPERIENCE) != null) {
						candidateObj.setRelevantExperience(
								Double.valueOf(candidateRequestMap.get(RELEVANT_EXPERIENCE).toString()));
					}

					if (candidateRequestMap.containsKey(LAST_COMPANY)
							&& candidateRequestMap.get(LAST_COMPANY) != null) {
						candidateObj.setLastCompany((String) candidateRequestMap.get(LAST_COMPANY));
					}

					if (checkForKeyExistanceAndNotNull(candidateRequestMap, OFFER_STATUS)) {
						Map<String, Object> offerStatusMap = (Map<String, Object>) candidateRequestMap
								.get(OFFER_STATUS);
						if (checkForKeyExistanceAndNotNull(offerStatusMap, CommonConstants.STATUS)) {
							Long offerStatusId = Long.valueOf(offerStatusMap.get(CommonConstants.STATUS).toString());
							String comment = "";
							if (checkForKeyExistanceAndNotNull(offerStatusMap, CommonConstants.COMMENT)) {
								comment = (String) offerStatusMap.get(CommonConstants.COMMENT);
							}
							Optional<OfferStatus> offerStatus = offerStatusRepository.findById(offerStatusId);
							if (offerStatus.isPresent()) {
								Offer offer = new Offer();
								offer.setStatus(offerStatus.get());
								offer.setComment(comment);
								if (offerStatus.get().getStatus().equalsIgnoreCase(CommonConstants.DECLINED)) {
									if (checkForKeyExistanceAndNotNull(offerStatusMap, CommonConstants.CATEGORY)) {
										Optional<OfferDeclineCategory> offerDeclineCategory = offerDeclineRepository
												.findById(Long.valueOf(
														offerStatusMap.get(CommonConstants.CATEGORY).toString()));
										if (offerDeclineCategory.isPresent()) {
											offer.setDeclineCategory(offerDeclineCategory.get());
										} else {
											String[] exception = new String[] {
													"Invalid candidate request, Invalid category supplied." };
											throwException(exception);
										}
									} else {
										String[] exception = new String[] {
												"Invalid candidate request, Decline category must be specified" };
										throwException(exception);
									}
								}
								candidateObj.setCandidateOfferStatus(offer);
							} else {
								String[] exception = new String[] {
										"Invalid candidate request, Invalid Offer Status Requested" };
								throwException(exception);
							}
						} else {
							String[] exception = new String[] {
									"Invalid candidate request, Offer Status must be specified" };
							throwException(exception);
						}
					}

					candidateResourceMapper(candidateRequestMap, candidateObj);
					onBoardStatusMapper(candidateRequestMap, candidateObj);
					financialYearMapper(candidateRequestMap, candidateObj);
					marketSubBusinessLineMapper(candidateRequestMap, candidateObj, true);
					competencyMapper(candidateRequestMap, candidateObj, true);

					candidateObj.setUpdatedAt(new Date());
					return candidateJPARepository.save(candidateObj);
				} else {
					String[] exception = new String[] { "Invalid candidate request, Invalid Candidate Id" };
					throwException(exception);
				}
			} else {
				String[] exception = new String[] {
						"Invalid candidate request, either the candidate id is missing or null" };
				throwException(exception);
			}
		} else {
			String[] exception = new String[] { "Invalid candidate request, request body can't be empty or null" };
			throwException(exception);
		}
		return null;
	}

	boolean checkForKeyExistanceAndNotNull(Map<String, Object> map, String key) {
		return (map.containsKey(key) && map.get(key) != null);
	}

	private void candidateResourceMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.CSV_CANDIDATE_SOURCE_KEY)) {
			Long candidateSourceId = Long
					.valueOf(candidateRequestMap.get(CommonConstants.CSV_CANDIDATE_SOURCE_KEY).toString());
			Optional<CandidateSources> cs = candidateSourceJPARepository.findById(candidateSourceId);
			if (cs.isPresent()) {
				candidateObj.setCandidateSource(cs.get());
			} else {
				String[] exception = new String[] { "Invalid candidate request, Invalid candidateSourceId passed" };
				throwException(exception);
			}
		}
	}

	private void onBoardStatusMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.CSV_ONBOARD_STATUS_KEY)) {
			Long onboardStatusId = Long
					.valueOf(candidateRequestMap.get(CommonConstants.CSV_ONBOARD_STATUS_KEY).toString());
			Optional<OnboardStatus> cs = onboardJPARepository.findById(onboardStatusId);
			if (cs.isPresent()) {
				candidateObj.setOnboardStatus(cs.get());
			} else {
				String[] exception = new String[] { "Invalid candidate request, Invalid Onboard Status passed" };
				throwException(exception);
			}
		}
	}

	private void financialYearMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.CSV_FINANCIAL_YEAR_KEY)) {
			Long financialYearId = Long
					.valueOf(candidateRequestMap.get(CommonConstants.CSV_FINANCIAL_YEAR_KEY).toString());
			Optional<FinancialYear> cs = financialYearRepository.findById(financialYearId);
			if (cs.isPresent()) {
				candidateObj.setFinancialYear(cs.get());
			} else {
				String[] exception = new String[] { "Invalid candidate request, Invalid fianancial year passed" };
				throwException(exception);
			}
		}
	}

	private void marketSubBusinessLineMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj,
			boolean modifyRequest) throws BaseException {
		if (!modifyRequest) {
			if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap,
					CommonConstants.CSV_MARKET_SUB_BUSINESS_LINE_KEY)) {
				marketSubBusinessLineMapperInner(candidateRequestMap, candidateObj);
			} else {
				String[] exception = new String[] {
						"Invalid candidate request, Market offering or sub business line missing from request" };
				throwException(exception);
			}
		} else {
			if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap,
					CommonConstants.CSV_MARKET_SUB_BUSINESS_LINE_KEY)) {
				marketSubBusinessLineMapperInner(candidateRequestMap, candidateObj);
			}
		}
	}

	private void marketSubBusinessLineMapperInner(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		Long subBusinessLine = Long
				.valueOf(candidateRequestMap.get(CommonConstants.CSV_MARKET_SUB_BUSINESS_LINE_KEY).toString());
		Optional<SubBusinessLine> cs = marketOfferingSubBusinessLineRepo.findById(subBusinessLine);
		if (cs.isPresent()) {
			candidateObj.setSubBusinessLine(cs.get());
		} else {
			String[] exception = new String[] {
					"Invalid candidate request, Invalid market offering or sub business line passed" };
			throwException(exception);
		}
	}

	private void competencyMapper(Map<String, Object> candidateRequestMap, Candidate candidateObj,
			boolean modifyRequest) throws BaseException {
		if (!modifyRequest) {
			if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.CSV_SUB_COMPETENCY_KEY)) {
				competencyMapperInner(candidateRequestMap, candidateObj);
			} else {
				String[] exception = new String[] { "Invalid candidate request, competency missing from request" };
				throwException(exception);
			}
		} else {
			if (Utils.checkCollectionHasKeyAndValue(candidateRequestMap, CommonConstants.CSV_SUB_COMPETENCY_KEY)) {
				competencyMapperInner(candidateRequestMap, candidateObj);
			}
		}
	}

	private void competencyMapperInner(Map<String, Object> candidateRequestMap, Candidate candidateObj)
			throws BaseException {
		Long competencyId = Long.valueOf(candidateRequestMap.get(CommonConstants.CSV_SUB_COMPETENCY_KEY).toString());
		Optional<SubCompetency> cs = subCompetencyRepository.findById(competencyId);
		if (cs.isPresent()) {
			candidateObj.setSubCompetency(cs.get());
		} else {
			String[] exception = new String[] { "Invalid candidate request, Invalid competency passed" };
			throwException(exception);
		}
	}

	private void throwException(String[] exception) throws BaseException {
		throw new BaseException(CandidateServiceImpl.class, exception);
	}

	private boolean checkIfSomeNameIsPresent(Map<String, Object> request) {
		return ((request.containsKey(FIRST_NAME) && request.get(FIRST_NAME) != null)
				|| (request.containsKey(MIDDLE_NAME) && request.get(MIDDLE_NAME) != null)
				|| (request.containsKey(LAST_NAME) && request.get(LAST_NAME) != null));
	}

	private boolean checkIfSomeContactNumberIsPresent(Map<String, Object> request) {
		return ((request.containsKey(PRIMARY_CONTACT) && request.get(PRIMARY_CONTACT) != null)
				|| (request.containsKey(SECONDARY_CONTACT) && request.get(SECONDARY_CONTACT) != null));
	}

	private boolean isEmailValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	@Override
	public List<String> saveCandidate(List<Map<String, Object>> candidateRequestMap) throws BaseException {
		List<String> cs = new ArrayList<>();
		candidateRequestMap.forEach(map -> {
			try {
				Candidate c = saveCandidate(map);
				cs.add(c.getFirstName() + " Created Succfully");
			} catch (BaseException e) {

			}
		});
		return cs;
	}

	@Override
	public Map<String, Object> listCandidates(Integer skip, Integer top, String sortField, String sortDirection,
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
			query.orderBy(builder.desc(r.get("id")));
		}

		List<Candidate> result = entityManager.createQuery(query).setFirstResult(skip).setMaxResults(top)
				.getResultList();
		for (Candidate candidate : result) {
			candidate.setFullName((candidate.getFirstName() == null ? "" : candidate.getFirstName())
					.concat(candidate.getMiddleName() == null ? " " : " " + candidate.getMiddleName())
					.concat(candidate.getLastName() == null ? " " : " " + candidate.getLastName()));
			candidate.setSourceName(
					candidate.getCandidateSource() != null ? candidate.getCandidateSource().getCandidateSource()
							: "--");
			candidate.setOnboardStatusValue(
					candidate.getOnboardStatus() != null ? candidate.getOnboardStatus().getOnboardStatus() : "--");
			candidate.setMarketOfferingBusinessLine(candidate.getSubBusinessLine() != null
					? candidate.getSubBusinessLine().getMarketOffering().getMarket()
					: "--");
			candidate.setCompetencyValue(
					candidate.getSubCompetency() != null ? candidate.getSubCompetency().getSubCompetency() : "--");
		}

		int totalCount = entityManager.createQuery(query).getResultList().size();

		Map<String, Object> dataToBeReturned = new HashMap<>();
		dataToBeReturned.put(CommonConstants.RESULTS, result);
		dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);

		return dataToBeReturned;

	}

	@Override
	public Object saveCandidate(MultipartFile file) throws IOException, BaseException {
		File convFile = new File(file.getOriginalFilename());
		boolean createNewFileStatu = convFile.createNewFile();
		if (!createNewFileStatu) {
			throw new IOException("Not able to create file");
		}

		/*
		 * Here we need to read the defined template, if this is there only then we 'll
		 * maintain and go ahead otherwise throw exception
		 */
		Optional<Report> reportTemplate = reportRepo.findByReportName(CommonConstants.CANDIDATE_REPORT_TEMPLATE);
		if (!reportTemplate.isPresent()) {
			throw new IOException("No Report Template Found");
		}

		String headers[] = reportTemplate.get().getKeys().split(",");

		/*
		 * Here entire CSV is being read and returned with allData
		 */
		try (FileOutputStream fos = new FileOutputStream(convFile)) {
			fos.write(file.getBytes());
		}

		CSVReader csvReader = new CSVReaderBuilder(new FileReader(convFile)).withSkipLines(0).build();
		List<String[]> allData = csvReader.readAll();

		/*
		 * In this block we compare csv headers with defined template
		 */
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
			throw new BaseException(CandidateServiceImpl.class,
					"CSV Headers Mismatch! Expected Headers are " + headerRow);
		}

		allData.remove(0);

		int completeData = allData.size();
		List<Map<String, Object>> candidatesRequestMap = new ArrayList<>();
		for (int i = 0; i < completeData; i++) {
			Map<String, Object> candidateMap = populateCandidateMap(allData.get(i), headers);
			if (candidateMap != null) {
				candidatesRequestMap.add(candidateMap);
			}
		}

		return saveCandidate(candidatesRequestMap);
	}

	private Map<String, Object> populateCandidateMap(String[] rowData, String headers[]) {
		int totalHeader = headers.length;
		if (totalHeader == rowData.length) {
			Map<String, Object> candidateMap = new HashMap<>();
			for (int i = 0; i < totalHeader; i++) {
				String header = headers[i];
				String value = rowData[i];
				if (header.equals(CommonConstants.CSV_CANDIDATE_SOURCE_KEY)) {
					Optional<CandidateSources> persistedObject = checkIfLongValueSpecified(value)
							? candidateSourceJPARepository.findById(Long.valueOf(value))
							: candidateSourceJPARepository.findByCandidateSource(value);
					if (persistedObject.isPresent()) {
						candidateMap.put(header, persistedObject.get().getId());
					}
				} else if (header.equals(CommonConstants.CSV_ONBOARD_STATUS_KEY)) {
					Optional<OnboardStatus> persistedObject = checkIfLongValueSpecified(value)
							? onboardJPARepository.findById(Long.valueOf(value))
							: onboardJPARepository.findByOnboardStatus(value);
					if (persistedObject.isPresent()) {
						candidateMap.put(header, persistedObject.get().getId());
					} else {
						System.out.println("Wrong OnBoardStatus= " + value + " for Header= " + header);
					}
				} else if (header.equals(CommonConstants.CSV_FINANCIAL_YEAR_KEY)) {
					Optional<FinancialYear> persistedObject = checkIfLongValueSpecified(value)
							? financialYearRepository.findById(Long.valueOf(value))
							: financialYearRepository.findByFinancialYear(value);
					if (persistedObject.isPresent()) {
						candidateMap.put(header, persistedObject.get().getId());
					}
				} else if (header.equals(CommonConstants.CSV_MARKET_SUB_BUSINESS_LINE_KEY)) {
					String marketOffering = candidateMap.get(CommonConstants.CSV_MARKET_OFFERING_KEY).toString();
					Optional<SubBusinessLine> persistedObject = checkIfLongValueSpecified(value)
							? marketOfferingSubBusinessLineRepo.findById(Long.valueOf(value))
							: marketOfferingSubBusinessLineRepo.findBySubBusinessLineAndMarketOffering(value,
									marketOffering);
					if (persistedObject.isPresent()) {
						candidateMap.put(header, persistedObject.get().getId());
					}
				} else if (header.equals(CommonConstants.CSV_SUB_COMPETENCY_KEY)) {
					String competency = candidateMap.get(CommonConstants.CSV_COMPETENCY_KEY).toString();
					Optional<SubCompetency> persistedObject = checkIfLongValueSpecified(value)
							? subCompetencyRepository.findById(Long.valueOf(value))
							: subCompetencyRepository.findBySubCompetencyAndCompetency(value, competency);
					if (persistedObject.isPresent()) {
						candidateMap.put(header, persistedObject.get().getId());
					}
				} else {
					candidateMap.put(header, value);
				}
			}
			return candidateMap;
		}
		return null;
	}

	private boolean checkIfLongValueSpecified(String value) {
		return value.matches("-?\\d+(\\.\\d+)?");
	}

	@Override
	public Object offerStatusUpdate(Map<String, Object> candidateRequestMap) throws BaseException {
		Long offerStatus = Utils.checkCollectionHasKeyAndValue(candidateRequestMap, "offerStatus")
				? Long.valueOf(candidateRequestMap.get("offerStatus").toString())
				: 0L;
		Long declineCategory = Utils.checkCollectionHasKeyAndValue(candidateRequestMap, "offerDeclineCategory")
				? Long.valueOf(candidateRequestMap.get("offerDeclineCategory").toString())
				: 0L;
		String comment = Utils.checkCollectionHasKeyAndValue(candidateRequestMap, "comment")
				? candidateRequestMap.get("comment").toString()
				: "";
		Long candidateId = Utils.checkCollectionHasKeyAndValue(candidateRequestMap, "candidateId")
				? Long.valueOf(candidateRequestMap.get("candidateId").toString())
				: 0L;
		
		Offer offer = new Offer();
		offer.setComment(comment);
		
		OfferStatus of = new OfferStatus();
		// of.setId(offerStatus);
		
		OfferDeclineCategory odc = new OfferDeclineCategory();
		// odc.setId(declineCategory);
		
		offer.setDeclineCategory(odc);
		offer.setStatus(of);
		
		Optional<Candidate> candidate = candidateJPARepository.findById(candidateId);
		if(candidate.isPresent()) {
			Candidate c = candidate.get();
			c.setCandidateOfferStatus(offer);
			return candidateJPARepository.save(c);
		}
		
		return null;
	}

}
