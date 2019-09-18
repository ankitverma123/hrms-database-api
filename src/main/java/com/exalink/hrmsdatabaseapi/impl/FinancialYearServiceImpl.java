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
import com.exalink.hrmsdatabaseapi.entity.candidate.FinancialYear;
import com.exalink.hrmsdatabaseapi.repository.IFinancialYearRepository;
import com.exalink.hrmsdatabaseapi.service.IFinancialYearService;
import com.exalink.hrmsdatabaseapi.utils.FiltersPredicateUtil;

/**
 * @author ankitkverma
 *
 */
@Service
public class FinancialYearServiceImpl implements IFinancialYearService{

	@Autowired
	private IFinancialYearRepository financialYearRepo;
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<FinancialYear> listAll() throws BaseException {
		return financialYearRepo.findAll();
	}

	@Override
	public Map<String, Object> list(Integer skip, Integer top, String sortField, String sortDirection, String filter)
			throws BaseException {
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
	
}
