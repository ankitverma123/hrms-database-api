package com.exalink.hrmsdatabaseapi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.CommonConstants;
import com.exalink.hrmsdatabaseapi.entity.candidate.CandidateSources;
import com.exalink.hrmsdatabaseapi.entity.candidate.FinancialYear;
import com.exalink.hrmsdatabaseapi.entity.candidate.OnboardStatus;
import com.exalink.hrmsdatabaseapi.entity.market.MarketOffering;
import com.exalink.hrmsdatabaseapi.entity.offer.OfferDeclineCategory;
import com.exalink.hrmsdatabaseapi.repository.ICandidateSourceRepository;
import com.exalink.hrmsdatabaseapi.repository.IFinancialYearRepository;
import com.exalink.hrmsdatabaseapi.repository.IMarketOfferingRepository;
import com.exalink.hrmsdatabaseapi.repository.IOfferDeclineRepository;
import com.exalink.hrmsdatabaseapi.repository.IOnBoardRepository;
import com.exalink.hrmsdatabaseapi.service.ISQLService;
import com.exalink.hrmsdatabaseapi.utils.FiltersPredicateUtil;
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
	
	@Override
	public Map<String, Object> listFinancialYear(Integer skip, Integer top, String sortField, String sortDirection,
			String filter) throws BaseException {
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
		}

		List<FinancialYear> result = entityManager.createQuery(query).setFirstResult(skip).setMaxResults(top)
				.getResultList();

		int totalCount = entityManager.createQuery(query).getResultList().size();

		Map<String, Object> dataToBeReturned = new HashMap<>();
		dataToBeReturned.put(CommonConstants.RESULTS, result);
		dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);

		return dataToBeReturned;
	}

	@Override
	public Map<String, Object> listCandidateSources(Integer skip, Integer top, String sortField, String sortDirection,
			String filter) throws BaseException {
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

		List<CandidateSources> result = entityManager.createQuery(query).setFirstResult(skip).setMaxResults(top)
				.getResultList();
		
		int totalCount = entityManager.createQuery(query).getResultList().size();

		Map<String, Object> dataToBeReturned = new HashMap<>();
		dataToBeReturned.put(CommonConstants.RESULTS, result);
		dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);

		return dataToBeReturned;
	}

	@Override
	public Map<String, Object> listOnboardStatus(Integer skip, Integer top, String sortField, String sortDirection,
			String filter) throws BaseException {
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

		List<OnboardStatus> result = entityManager.createQuery(query).setFirstResult(skip).setMaxResults(top)
				.getResultList();
		
		int totalCount = entityManager.createQuery(query).getResultList().size();

		Map<String, Object> dataToBeReturned = new HashMap<>();
		dataToBeReturned.put(CommonConstants.RESULTS, result);
		dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);

		return dataToBeReturned;
	}

	@Override
	public Map<String, Object> listOfferDeclineCategories(Integer skip, Integer top, String sortField,
			String sortDirection, String filter) throws BaseException {
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

		List<OfferDeclineCategory> result = entityManager.createQuery(query).setFirstResult(skip).setMaxResults(top)
				.getResultList();
		
		int totalCount = entityManager.createQuery(query).getResultList().size();

		Map<String, Object> dataToBeReturned = new HashMap<>();
		dataToBeReturned.put(CommonConstants.RESULTS, result);
		dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);

		return dataToBeReturned;
	}

	@Override
	public Object persist(String path, Map<String, Object> requestMap) throws BaseException {
		if(path.equalsIgnoreCase(CommonConstants.FINANCIAL_YEAR)) {
			FinancialYear obj = new ObjectMapper().convertValue(requestMap, FinancialYear.class);
			if(obj!=null && !obj.getFinancialYear().isEmpty()) {
				if(financialYearRepo.findByFinancialYear(obj.getFinancialYear()).isPresent()) {
					String[] exception = new String[] { obj.getFinancialYear()+" financial year already there" };
					throwException(exception);
				}	
				return financialYearRepo.save(obj);
			}
		} else if(path.equalsIgnoreCase(CommonConstants.CANDIDATE_SOURCE)) {
			CandidateSources obj = new ObjectMapper().convertValue(requestMap, CandidateSources.class);
			if(obj!=null && !obj.getName().isEmpty()) {
				if(candidateSourceRepository.findByName(obj.getName()).isPresent()) {
					String[] exception = new String[] { obj.getName()+ " candidate source already there" };
					throwException(exception);
				}
				obj.setIsActive(true);
				return candidateSourceRepository.save(obj);
			}
		} else if(path.equalsIgnoreCase(CommonConstants.ONBOARD_STATUS)) {
			OnboardStatus obj = new ObjectMapper().convertValue(requestMap, OnboardStatus.class);
			if(obj!=null && !obj.getStatus().isEmpty()) {
				if(onboardRepository.findByStatus(obj.getStatus()).isPresent()) {
					String[] exception = new String[] { obj.getStatus()+ " onboard status already there" };
					throwException(exception);
				}
				return onboardRepository.save(obj);
			}
		} else if(path.equalsIgnoreCase(CommonConstants.OFFER_DECLINE_CATEGORIES)) {
			OfferDeclineCategory obj =new ObjectMapper().convertValue(requestMap, OfferDeclineCategory.class);
			if(obj!=null && !obj.getCategory().isEmpty()) {
				if(offerDeclineRepository.findByCategory(obj.getCategory()).isPresent()) {
					String[] exception = new String[] { obj.getCategory()+ " category already there" };
					throwException(exception);
				}
				return offerDeclineRepository.save(obj);
			}
		} else if(path.equalsIgnoreCase(CommonConstants.MARKET_OFFERING)) {
			MarketOffering obj =new ObjectMapper().convertValue(requestMap, MarketOffering.class);
			if(obj!=null && !obj.getMarket().isEmpty()) {
				if(marketOfferingRepository.findByMarket(obj.getMarket()).isPresent()) {
					String[] exception = new String[] { obj.getMarket()+ " market offering already there" };
					throwException(exception);
				}
				return marketOfferingRepository.save(obj);
			}
		}
		return null;
	}

	private void throwException(String[] exception) throws BaseException {
		throw new BaseException(CandidateServiceImpl.class, exception);
	}

	@Override
	public Map<String, Object> listMarketOffering(Integer skip, Integer top, String sortField, String sortDirection,
			String filter) throws BaseException {
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

		List<MarketOffering> result = entityManager.createQuery(query).setFirstResult(skip).setMaxResults(top)
				.getResultList();

		int totalCount = entityManager.createQuery(query).getResultList().size();

		Map<String, Object> dataToBeReturned = new HashMap<>();
		dataToBeReturned.put(CommonConstants.RESULTS, result);
		dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);

		return dataToBeReturned;
	}
}
