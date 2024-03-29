package com.exalink.hrmsdatabaseapi.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.entity.FileTracking;
import com.exalink.hrmsdatabaseapi.entity.candidate.CandidateSources;
import com.exalink.hrmsdatabaseapi.entity.candidate.FinancialYear;
import com.exalink.hrmsdatabaseapi.entity.candidate.OnboardStatus;
import com.exalink.hrmsdatabaseapi.entity.competency.Competency;
import com.exalink.hrmsdatabaseapi.entity.market.MarketOffering;
import com.exalink.hrmsdatabaseapi.entity.offer.OfferDeclineCategory;
import com.exalink.hrmsdatabaseapi.entity.offer.OfferStatus;
import com.exalink.hrmsdatabaseapi.repository.ICandidateSourceRepository;
import com.exalink.hrmsdatabaseapi.repository.ICompetencyRepository;
import com.exalink.hrmsdatabaseapi.repository.IFileTrackingRepository;
import com.exalink.hrmsdatabaseapi.repository.IFinancialYearRepository;
import com.exalink.hrmsdatabaseapi.repository.IMarketOfferingRepository;
import com.exalink.hrmsdatabaseapi.repository.IOfferDeclineRepository;
import com.exalink.hrmsdatabaseapi.repository.IOnBoardRepository;
import com.exalink.hrmsdatabaseapi.service.ISQLService;
import com.exalink.hrmsdatabaseapi.utils.FiltersPredicateUtil;
import com.exalink.hrmsfabric.common.BaseException;
import com.exalink.hrmsfabric.common.CommonConstants;
import com.exalink.hrmsfabric.common.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ankitkverma
 *
 */
@Service
public class SQLServiceImpl implements ISQLService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private IFinancialYearRepository financialYearRepo;

	@Autowired
	private ICandidateSourceRepository candidateSourceRepository;

	@Autowired
	private IOnBoardRepository onboardRepository;

	@Autowired
	private IOfferDeclineRepository offerDeclineRepository;

	@Autowired
	private IMarketOfferingRepository marketOfferingRepository;
	
	@Autowired
	private IFileTrackingRepository fileTrackingRepository;
	
	@Autowired
	private ICompetencyRepository competencyRepository;

	private static final String ID = "id";
	private static final String MARKET = "market";
	private static final String CATEGORY = "category";
	private static final String ONBOARDSTATUS = "onboardStatus";
	private static final String CANDIDATESOURCE = "candidateSource";
	private static final String FIANNCIAL_YEAR = "financialYear";
	
	@Override
	public Object listFinancialYear(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter,
			boolean requestForDropDown) throws BaseException {
		List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FinancialYear> query = builder.createQuery(FinancialYear.class);
		Root<FinancialYear> r = query.from(FinancialYear.class);
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
		
		if (requestForDropDown) {
			List<FinancialYear> result = entityManager.createQuery(query).getResultList();
			List<Map<String, Object>> con = new ArrayList<>();
			for (FinancialYear fy : result) {
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.ID, fy.getId());
				map.put(CommonConstants.LABEL, fy.getFinancialYear());
				con.add(map);
			}
			return con;
		} else {
			List<FinancialYear> result = entityManager.createQuery(query).setFirstResult((pageNumber - 1) * pageSize)
					.setMaxResults(pageSize).getResultList();
			int totalCount = entityManager.createQuery(query).getResultList().size();

			Map<String, Object> dataToBeReturned = new HashMap<>();
			dataToBeReturned.put(CommonConstants.RESULTS, result);
			dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);
			dataToBeReturned.put(CommonConstants.LAST_PAGE, (int) ((totalCount / pageSize) + 1));

			return dataToBeReturned;
		}
	}

	@Override
	public Object listCandidateSources(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter,
			boolean requestForDropDown) throws BaseException {
		List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CandidateSources> query = builder.createQuery(CandidateSources.class);
		Root<CandidateSources> r = query.from(CandidateSources.class);
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
		}
		if (requestForDropDown) {
			List<CandidateSources> result = entityManager.createQuery(query).getResultList();
			List<Map<String, Object>> con = new ArrayList<>();
			for (CandidateSources fy : result) {
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.ID, fy.getId());
				map.put(CommonConstants.LABEL, fy.getCandidateSource());
				con.add(map);
			}
			return con;
		} else {
			List<CandidateSources> result = entityManager.createQuery(query).setFirstResult((pageNumber-1) * pageSize).setMaxResults(pageSize)
					.getResultList();

			int totalCount = entityManager.createQuery(query).getResultList().size();

			Map<String, Object> dataToBeReturned = new HashMap<>();
			dataToBeReturned.put(CommonConstants.RESULTS, result);
			dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);
			dataToBeReturned.put(CommonConstants.LAST_PAGE, (int)((totalCount/pageSize)+1));

			return dataToBeReturned;
		}
	}

	@Override
	public Object listOnboardStatus(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter,
			boolean requestForDropDown) throws BaseException {
		List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<OnboardStatus> query = builder.createQuery(OnboardStatus.class);
		Root<OnboardStatus> r = query.from(OnboardStatus.class);
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
		}

		if (requestForDropDown) {
			List<OnboardStatus> result = entityManager.createQuery(query).getResultList();
			List<Map<String, Object>> con = new ArrayList<>();
			for (OnboardStatus fy : result) {
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.ID, fy.getId());
				map.put(CommonConstants.LABEL, fy.getOnboardStatus());
				con.add(map);
			}
			return con;
		} else {
			List<OnboardStatus> result = entityManager.createQuery(query).setFirstResult((pageNumber-1) * pageSize).setMaxResults(pageSize)
					.getResultList();

			int totalCount = entityManager.createQuery(query).getResultList().size();

			Map<String, Object> dataToBeReturned = new HashMap<>();
			dataToBeReturned.put(CommonConstants.RESULTS, result);
			dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);
			dataToBeReturned.put(CommonConstants.LAST_PAGE, (int)((totalCount/pageSize)+1));

			return dataToBeReturned;
		}
	}

	@Override
	public Object listOfferDeclineCategories(Integer pageNumber, Integer pageSize, String sortField, String sortDirection,
			String filter, boolean requestForDropDown) throws BaseException {
		List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<OfferDeclineCategory> query = builder.createQuery(OfferDeclineCategory.class);
		Root<OfferDeclineCategory> r = query.from(OfferDeclineCategory.class);
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
		}

		
		if (requestForDropDown) {
			List<OfferDeclineCategory> result = entityManager.createQuery(query).getResultList();
			List<Map<String, Object>> con = new ArrayList<>();
			for (OfferDeclineCategory fy : result) {
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.ID, fy.getId());
				map.put(CommonConstants.LABEL, fy.getCategory());
				con.add(map);
			}
			return con;
		}else {
			List<OfferDeclineCategory> result = entityManager.createQuery(query).setFirstResult((pageNumber-1) * pageSize).setMaxResults(pageSize)
					.getResultList();

			int totalCount = entityManager.createQuery(query).getResultList().size();

			Map<String, Object> dataToBeReturned = new HashMap<>();
			dataToBeReturned.put(CommonConstants.RESULTS, result);
			dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);
			dataToBeReturned.put(CommonConstants.LAST_PAGE, (int)((totalCount/pageSize)+1));

			return dataToBeReturned;
		}
	}

	@Override
	public Object persist(String path, Map<String, Object> requestMap) throws BaseException {
		if (path.equalsIgnoreCase(CommonConstants.FINANCIAL_YEAR)) {
			FinancialYear obj = new ObjectMapper().convertValue(requestMap, FinancialYear.class);
			if (obj != null && !obj.getFinancialYear().isEmpty()) {
				if (financialYearRepo.findByFinancialYear(obj.getFinancialYear()).isPresent()) {
					String[] exception = new String[] { obj.getFinancialYear() + " financial year already there" };
					throwException(exception);
				}
				return financialYearRepo.save(obj);
			}
		} else if (path.equalsIgnoreCase(CommonConstants.CANDIDATE_SOURCE)) {
			CandidateSources obj = new ObjectMapper().convertValue(requestMap, CandidateSources.class);
			if (obj != null && !obj.getCandidateSource().isEmpty()) {
				if (candidateSourceRepository.findByCandidateSource(obj.getCandidateSource()).isPresent()) {
					String[] exception = new String[] { obj.getCandidateSource() + " candidate source already there" };
					throwException(exception);
				}
				obj.setIsActive(true);
				return candidateSourceRepository.save(obj);
			}
		} else if (path.equalsIgnoreCase(CommonConstants.ONBOARD_STATUS)) {
			OnboardStatus obj = new ObjectMapper().convertValue(requestMap, OnboardStatus.class);
			if (obj != null && !obj.getOnboardStatus().isEmpty()) {
				if (onboardRepository.findByOnboardStatus(obj.getOnboardStatus()).isPresent()) {
					String[] exception = new String[] { obj.getOnboardStatus() + " onboard status already there" };
					throwException(exception);
				}
				return onboardRepository.save(obj);
			}
		} else if (path.equalsIgnoreCase(CommonConstants.OFFER_DECLINE_CATEGORIES)) {
			OfferDeclineCategory obj = new ObjectMapper().convertValue(requestMap, OfferDeclineCategory.class);
			if (obj != null && !obj.getCategory().isEmpty()) {
				if (offerDeclineRepository.findByCategory(obj.getCategory()).isPresent()) {
					String[] exception = new String[] { obj.getCategory() + " category already there" };
					throwException(exception);
				}
				return offerDeclineRepository.save(obj);
			}
		} else if (path.equalsIgnoreCase(CommonConstants.MARKET_OFFERING)) {
			MarketOffering obj = new ObjectMapper().convertValue(requestMap, MarketOffering.class);
			if (obj != null && !obj.getMarket().isEmpty()) {
				if (marketOfferingRepository.findByMarket(obj.getMarket()).isPresent()) {
					String[] exception = new String[] { obj.getMarket() + " market offering already there" };
					throwException(exception);
				}
				return marketOfferingRepository.save(obj);
			}
		} else if(path.equals(CommonConstants.FILE_TRACKING)) {
			FileTracking obj = new ObjectMapper().convertValue(requestMap, FileTracking.class);
			if (obj != null && !obj.getDocumentUUID().isEmpty() && !obj.getFileName().isEmpty()) {
				return fileTrackingRepository.save(obj);
			}
		} else if(path.equals(CommonConstants.COMPETENCY)) {
			Competency obj = new ObjectMapper().convertValue(requestMap, Competency.class);
			if (obj != null && !obj.getCompetency().isEmpty()) {
				return competencyRepository.save(obj);
			}
		}
		return null;
	}

	private void throwException(String[] exception) throws BaseException {
		throw new BaseException(CandidateServiceImpl.class, exception);
	}

	@Override
	public Object listMarketOffering(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter,
			boolean requestForDropDown) throws BaseException {
		List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MarketOffering> query = builder.createQuery(MarketOffering.class);
		Root<MarketOffering> r = query.from(MarketOffering.class);
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
		}

		if (requestForDropDown) {
			List<MarketOffering> result = entityManager.createQuery(query).getResultList();
			List<Map<String, Object>> con = new ArrayList<>();
			for (MarketOffering fy : result) {
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.ID, fy.getId());
				map.put(CommonConstants.LABEL, fy.getMarket());
				con.add(map);
			}
			return con;
		} else {
			List<MarketOffering> result = entityManager.createQuery(query).setFirstResult((pageNumber-1) * pageSize).setMaxResults(pageSize)
					.getResultList();

			int totalCount = entityManager.createQuery(query).getResultList().size();
			
			List<Map<String, Object>> results = new ArrayList<>();
			for(MarketOffering mo : result) {
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.ID, mo.getId());
				map.put("market", mo.getMarket());
				map.put("isActive", mo.getIsActive());
				map.put("subBusinessLine", mo.getSubBusinessLine());
				results.add(map);
			}
			
			Map<String, Object> dataToBeReturned = new HashMap<>();
			dataToBeReturned.put(CommonConstants.RESULTS, results);
			dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);
			dataToBeReturned.put(CommonConstants.LAST_PAGE, (int)((totalCount/pageSize)+1));

			return dataToBeReturned;
		}
	}

	@Override
	public Object listCompetency(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter,
			boolean requestForDropDown) throws BaseException {
		List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Competency> query = builder.createQuery(Competency.class);
		Root<Competency> r = query.from(Competency.class);
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
		}
		if (requestForDropDown) {
			List<Competency> result = entityManager.createQuery(query).getResultList();
			List<Map<String, Object>> con = new ArrayList<>();
			for (Competency fy : result) {
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.ID, fy.getId());
				map.put(CommonConstants.LABEL, fy.getCompetency());
				con.add(map);
			}
			return con;
		} else {
			List<Competency> result = entityManager.createQuery(query).setFirstResult((pageNumber-1) * pageSize).setMaxResults(pageSize)
					.getResultList();

			int totalCount = entityManager.createQuery(query).getResultList().size();

			Map<String, Object> dataToBeReturned = new HashMap<>();
			dataToBeReturned.put(CommonConstants.RESULTS, result);
			dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);
			dataToBeReturned.put(CommonConstants.LAST_PAGE, (int)((totalCount/pageSize)+1));

			return dataToBeReturned;
		}
	}

	@Override
	public Object listFileTracking(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter,
			boolean requestForDropDown) throws BaseException {
		List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FileTracking> query = builder.createQuery(FileTracking.class);
		Root<FileTracking> r = query.from(FileTracking.class);
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
		}
		
		List<FileTracking> result = entityManager.createQuery(query).setFirstResult((pageNumber-1) * pageSize).setMaxResults(pageSize)
				.getResultList();

		int totalCount = entityManager.createQuery(query).getResultList().size();

		Map<String, Object> dataToBeReturned = new HashMap<>();
		dataToBeReturned.put(CommonConstants.RESULTS, result);
		dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);
		dataToBeReturned.put(CommonConstants.LAST_PAGE, (int)((totalCount/pageSize)+1));

		return dataToBeReturned;

	}

	@Override
	public Object listCandidateOfferStatus(Integer pageNumber, Integer pageSize, String sortField, String sortDirection,
			String filter, boolean requestForDropDown) throws BaseException {
		List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<OfferStatus> query = builder.createQuery(OfferStatus.class);
		Root<OfferStatus> r = query.from(OfferStatus.class);
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
		}
		
		if(requestForDropDown) {
			List<OfferStatus> result = entityManager.createQuery(query).getResultList();
			List<Map<String, Object>> con = new ArrayList<>();
			for (OfferStatus fy : result) {
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.ID, fy.getId());
				map.put(CommonConstants.LABEL, fy.getStatus());
				con.add(map);
			}
			return con;
		}else {
			List<OfferStatus> result = entityManager.createQuery(query).setFirstResult((pageNumber-1) * pageSize).setMaxResults(pageSize)
					.getResultList();
			int totalCount = entityManager.createQuery(query).getResultList().size();

			Map<String, Object> dataToBeReturned = new HashMap<>();
			dataToBeReturned.put(CommonConstants.RESULTS, result);
			dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);
			dataToBeReturned.put(CommonConstants.LAST_PAGE, (int)((totalCount/pageSize)+1));

			return dataToBeReturned;
		}
	}

	@Transactional
	@Override
	public Object update(String path, Map<String, Object> requestMap) throws BaseException {
		if (path.equalsIgnoreCase(CommonConstants.FINANCIAL_YEAR)
				&& Utils.checkCollectionHasKeyAndValue(requestMap, ID)
				&& Utils.checkCollectionHasKeyAndValue(requestMap, FIANNCIAL_YEAR)) {
			UUID id = UUID.fromString(requestMap.get(ID).toString());
			String valueToBeUpdated = requestMap.get(FIANNCIAL_YEAR).toString();
			String msg = updateConfigurations(id, valueToBeUpdated, "FinancialYear", FIANNCIAL_YEAR, "Financial year ");
			return generalStringResponse(msg);
		} else if (path.equalsIgnoreCase(CommonConstants.CANDIDATE_SOURCE)
				&& Utils.checkCollectionHasKeyAndValue(requestMap, ID)
				&& Utils.checkCollectionHasKeyAndValue(requestMap, CANDIDATESOURCE)) {
			UUID id = UUID.fromString(requestMap.get(ID).toString());
			String valueToBeUpdated = requestMap.get(CANDIDATESOURCE).toString();
			String msg =  updateConfigurations(id, valueToBeUpdated, "CandidateSources", CANDIDATESOURCE, "Candidate source");
			return generalStringResponse(msg);
		} else if (path.equalsIgnoreCase(CommonConstants.ONBOARD_STATUS)
				&& Utils.checkCollectionHasKeyAndValue(requestMap, ID)
				&& Utils.checkCollectionHasKeyAndValue(requestMap, ONBOARDSTATUS)) {
			UUID id = UUID.fromString(requestMap.get(ID).toString());
			String valueToBeUpdated = requestMap.get(ONBOARDSTATUS).toString();
			String msg =  updateConfigurations(id, valueToBeUpdated, "OnboardStatus", ONBOARDSTATUS, "OnBoard status");
			return generalStringResponse(msg);
		} else if (path.equalsIgnoreCase(CommonConstants.OFFER_DECLINE_CATEGORIES)
				&& Utils.checkCollectionHasKeyAndValue(requestMap, ID)
				&& Utils.checkCollectionHasKeyAndValue(requestMap, CATEGORY)) {
			UUID id = UUID.fromString(requestMap.get(ID).toString());
			String valueToBeUpdated = requestMap.get(CATEGORY).toString();
			String msg =  updateConfigurations(id, valueToBeUpdated, "OfferDeclineCategory", CATEGORY, "Offer decline category");
			return generalStringResponse(msg);
		} else if (path.equalsIgnoreCase(CommonConstants.MARKET_OFFERING)
				&& Utils.checkCollectionHasKeyAndValue(requestMap, ID)
				&& Utils.checkCollectionHasKeyAndValue(requestMap, MARKET)) {
			UUID id = UUID.fromString(requestMap.get(ID).toString());
			String valueToBeUpdated = requestMap.get(MARKET).toString();
			String msg =  updateConfigurations(id, valueToBeUpdated, "MarketOffering", MARKET, "Market offering");
			return generalStringResponse(msg);
		}
		return null;
	}
	
	private String updateConfigurations(UUID id, String valueToBeUpdated, String table, String columnToBeUpdated, String entity) throws BaseException {
		String selectStatement = "SELECT CASE WHEN COUNT(entityObj) > 0 THEN true ELSE false END FROM ";
		String queryBasedOnId = selectStatement + table+" entityObj where entityObj.id = '" + id + "'";
		String queryBasedOnAttribute = selectStatement +table+" entityObj where entityObj.id <> '" + id + "' and entityObj."+columnToBeUpdated+" = '"+valueToBeUpdated+"'";
		String updateQuery = "UPDATE "+table+" entityObj SET entityObj."+columnToBeUpdated+"='"+valueToBeUpdated+"',entityObj.updatedAt='"+new Date()+"' WHERE entityObj.id='"+id+"'";
		
		if (executeCustomQuery(queryBasedOnId)) {
			if (executeCustomQuery(queryBasedOnAttribute)) {
				throw new BaseException(SQLServiceImpl.class, valueToBeUpdated + " is already being used");
			}
			int recordUpdated = updateQuery(updateQuery);
			if(recordUpdated > 0) {
				return entity+ " updated successfully";
			}
			return "Failed to update "+entity;
		} else {
			throw new BaseException(SQLServiceImpl.class, "Invalid request, No "+entity+" found for id: " + id);
		}
	}
	
	private boolean executeCustomQuery(String query) {
		TypedQuery<Boolean> resultSet = entityManager.createQuery(query, Boolean.class);
		if(resultSet!=null)
			return resultSet.getSingleResult();
		return false;
	}
	
	@Transactional
	private int updateQuery(String updateQuery) {
		return entityManager.createQuery(updateQuery).executeUpdate();
	}

	@Transactional
	@Override
	public Object delete(String path, String uuid) throws BaseException {
		UUID entityUUID = null;
		String query = "";
		switch (path) {
		case CommonConstants.FINANCIAL_YEAR:
			entityUUID = UUID.fromString(uuid);
			query+="DELETE FROM FinancialYear Where id = '"+entityUUID+"'";
			break;
		case CommonConstants.CANDIDATE_SOURCE:
			entityUUID = UUID.fromString(uuid);
			query+="DELETE FROM CandidateSources Where id = '"+entityUUID+"'";
			break;
		case CommonConstants.ONBOARD_STATUS:
			entityUUID = UUID.fromString(uuid);
			query+="DELETE FROM OnboardStatus Where id = '"+entityUUID+"'";
			break;
		case CommonConstants.OFFER_DECLINE_CATEGORIES:
			entityUUID = UUID.fromString(uuid);
			query+="DELETE FROM OfferDeclineCategory Where id = '"+entityUUID+"'";
			break;
		case CommonConstants.MARKET_OFFERING:
			entityUUID = UUID.fromString(uuid);
			query+="DELETE FROM MarketOffering Where id = '"+entityUUID+"'";
			break;
		case CommonConstants.CANDIDATE:
			entityUUID = UUID.fromString(uuid);
			query+="DELETE FROM Candidate Where id = '"+entityUUID+"'";
			break;
		case CommonConstants.COMPETENCY:
			entityUUID = UUID.fromString(uuid);
			query+="DELETE FROM Competency where id = '"+entityUUID+"'";
			break;
		default:
				break;
		}
		if (query != null && !query.isEmpty()) {
			int recordUpdated = updateQuery(query);
			if (recordUpdated > 0) {
				return generalStringResponse(path + " deleted successfully");
			}
		}
		return generalStringResponse("Failed to delete "+path);
	}
		
	private Map<String, Object> generalStringResponse(String message) {
		Map<String, Object> map= new HashMap<>();
		map.put("message", message);
		return map;
	}
}
