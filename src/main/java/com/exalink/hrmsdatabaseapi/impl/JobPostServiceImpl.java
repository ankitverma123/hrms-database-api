package com.exalink.hrmsdatabaseapi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.entity.competency.Competency;
import com.exalink.hrmsdatabaseapi.entity.competency.SubCompetency;
import com.exalink.hrmsdatabaseapi.entity.jobRequirement.JobPost;
import com.exalink.hrmsdatabaseapi.entity.market.MarketOffering;
import com.exalink.hrmsdatabaseapi.entity.market.SubBusinessLine;
import com.exalink.hrmsdatabaseapi.repository.IJobPostRepository;
import com.exalink.hrmsdatabaseapi.service.IJobPostService;
import com.exalink.hrmsdatabaseapi.utils.FiltersPredicateUtil;
import com.exalink.hrmsfabric.common.BaseException;
import com.exalink.hrmsfabric.common.CommonConstants;
import com.exalink.hrmsfabric.common.Utils;

@Service
public class JobPostServiceImpl implements IJobPostService {

	private static final String TITLE = "title";
	private static final String MIN_EXPERIENCE = "minExperience";
	private static final String MAX_EXPERIENCE = "maxExperience";
	private static final String VACANCIES = "vacancies";
	private static final String SOLE_RESPONSIBILITY = "soleResponsibility";
	private static final String SKILLS_REQUIRED = "skillsRequired";
	private static final String MARKET_OFFERING = "marketOffering";
	private static final String SUB_BUSINESS_LINE = "subBusinessLine";
	private static final String COMPETENCY = "competency";
	private static final String SUB_COMPETENCY = "subCompetency";
	private static final String LOCATION = "location";
	private static final String ADDITIONAL_COMMENTS = "additionalComments";
	
	@Autowired
	private IJobPostRepository jobPostRepo;

	@Autowired
	private EntityManager entityManager;
	
	@Transactional
	@Override
	public Object save(Map<String, Object> requestMap) throws BaseException {
		if (requestMap != null && !requestMap.isEmpty()) {
			JobPost jobPost = new JobPost();

			if (Utils.checkCollectionHasKeyAndValue(requestMap, TITLE))
				jobPost.setTitle((String) requestMap.get(TITLE));

			if (Utils.checkCollectionHasKeyAndValue(requestMap, MIN_EXPERIENCE))
				jobPost.setMinExperience(Double.valueOf(requestMap.get(MIN_EXPERIENCE).toString()));

			if (Utils.checkCollectionHasKeyAndValue(requestMap, MAX_EXPERIENCE))
				jobPost.setMaxExperience(Double.valueOf(requestMap.get(MAX_EXPERIENCE).toString()));

			if (Utils.checkCollectionHasKeyAndValue(requestMap, VACANCIES))
				jobPost.setVacancies(Long.valueOf(requestMap.get(VACANCIES).toString()));

			if (Utils.checkCollectionHasKeyAndValue(requestMap, SOLE_RESPONSIBILITY))
				jobPost.setSoleResponsibility((String) requestMap.get(SOLE_RESPONSIBILITY));

			if (Utils.checkCollectionHasKeyAndValue(requestMap, SKILLS_REQUIRED))
				jobPost.setSkillsRequired(((String) requestMap.get(SKILLS_REQUIRED)).split(","));

			if (Utils.checkCollectionHasKeyAndValue(requestMap, ADDITIONAL_COMMENTS))
				jobPost.setAdditionalComments((String) requestMap.get(ADDITIONAL_COMMENTS));
			
			if (Utils.checkCollectionHasKeyAndValue(requestMap, LOCATION))
				jobPost.setLocation((String) requestMap.get(LOCATION));
			
			if (Utils.checkCollectionHasKeyAndValue(requestMap, MARKET_OFFERING)) {
				String uuid = requestMap.get(MARKET_OFFERING).toString();
				if (Utils.checkIfUUID(uuid)) {
					UUID marketOfferingId = UUID.fromString(uuid);
					Object entityObject = entityUUIDVerifier(marketOfferingId, "MarketOffering");
					if(entityObject!=null) {
						jobPost.setMarketOffering((MarketOffering)entityObject);
					}
				} else {
					String[] exception = new String[] { CommonConstants.INVALID_UUID_ERROR_MESSAGE };
					throwException(exception);
				}
			}

			if (Utils.checkCollectionHasKeyAndValue(requestMap, SUB_BUSINESS_LINE)) {
				String uuid = requestMap.get(SUB_BUSINESS_LINE).toString();
				if (Utils.checkIfUUID(uuid)) {
					UUID marketOfferingId = UUID.fromString(uuid);
					Object entityObject = entityUUIDVerifier(marketOfferingId, "SubBusinessLine");
					if(entityObject!=null) {
						jobPost.setSubBusinessLine((SubBusinessLine)entityObject);
					}
				} else {
					String[] exception = new String[] { CommonConstants.INVALID_UUID_ERROR_MESSAGE };
					throwException(exception);
				}
			}

			if (Utils.checkCollectionHasKeyAndValue(requestMap, COMPETENCY)) {
				String uuid = requestMap.get(COMPETENCY).toString();
				if (Utils.checkIfUUID(uuid)) {
					UUID marketOfferingId = UUID.fromString(uuid);
					Object entityObject = entityUUIDVerifier(marketOfferingId, "Competency");
					if(entityObject!=null) {
						jobPost.setCompetency((Competency)entityObject);
					}
				} else {
					String[] exception = new String[] { CommonConstants.INVALID_UUID_ERROR_MESSAGE };
					throwException(exception);
				}
			}

			if (Utils.checkCollectionHasKeyAndValue(requestMap, SUB_COMPETENCY)) {
				String uuid = requestMap.get(SUB_COMPETENCY).toString();
				if (Utils.checkIfUUID(uuid)) {
					UUID marketOfferingId = UUID.fromString(uuid);
					Object entityObject = entityUUIDVerifier(marketOfferingId, "SubCompetency");
					if(entityObject!=null) {
						jobPost.setSubCompetency((SubCompetency)entityObject);
					}
				} else {
					String[] exception = new String[] { CommonConstants.INVALID_UUID_ERROR_MESSAGE };
					throwException(exception);
				}
			}

			return jobPostRepo.save(jobPost);
		} else {
			String[] exception = new String[] { "Invalid job post request, request body can't be empty or null" };
			throwException(exception);
		}
		return null;
	}

	private void throwException(String[] exception) throws BaseException {
		throw new BaseException(JobPostServiceImpl.class, exception);
	}

	@Transactional
	private Object entityUUIDVerifier(UUID id, String table) {
		String jpaQuery = "FROM "+table+" entityObj WHERE entityObj.id = '"+id+"'";
		return entityManager.createQuery(jpaQuery).getSingleResult();
	}

	@Override
	public Map<String, Object> queryService(Integer skip, Integer top, String sortField, String sortDirection,
			String filter) throws BaseException {
		List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<JobPost> query = builder.createQuery(JobPost.class);
		Root<JobPost> r = query.from(JobPost.class);
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

		List<JobPost> result = entityManager.createQuery(query).setFirstResult(skip).setMaxResults(top).getResultList();
		for(JobPost jp : result) {
			jp.setMarketOfferingTitle(jp.getMarketOffering().getMarket());
			jp.setMarketOfferingSubBusinessLineTitle(jp.getSubBusinessLine().getSubBusinessLine());
			jp.setCompetencyTitle(jp.getCompetency().getCompetency());
			jp.setSubCompetencyTitle(jp.getSubCompetency().getSubCompetency());
		}

		int totalCount = entityManager.createQuery(query).getResultList().size();

		Map<String, Object> dataToBeReturned = new HashMap<>();
		dataToBeReturned.put(CommonConstants.RESULTS, result);
		dataToBeReturned.put(CommonConstants.TOTAL_COUNT, totalCount);

		return dataToBeReturned;
	}

}
