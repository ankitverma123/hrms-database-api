package com.exalink.hrmsdatabaseapi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exalink.hrmsdatabaseapi.CommonConstants;
import com.exalink.hrmsdatabaseapi.Utils;
import com.exalink.hrmsdatabaseapi.repository.IRequestCategoryJPARepository;
import com.exalink.hrmsdatabaseapi.service.IRequestCategoryService;

/**
 * @author ankitkverma
 *
 */
@Service
public class RequestCategoryServiceImpl implements IRequestCategoryService {

	@Autowired
	private IRequestCategoryJPARepository requestCategoryRepo;

	@Autowired
	private RestTemplate restTemplate;
	
	private static final String LAYOUT = "layout";
	private static final String ELEMENTS = "elements";
	private static final String FIELD_SETS = "fieldSets";
	private static final String ID = "id";

	@Override
	@Transactional
	public Object loadRequestCategory(String name, String type, String modifyEntityId,
			HttpServletRequest request) {
		/*
		 * 1. First Load Request Category Based on Name and Type
		 */
		Map<String, Object> requestCategory = requestCategoryRepo.loadRequestCategory(name, type);
		if (Utils.checkCollectionHasKeyAndValue(requestCategory, ID)) {
			UUID requestCategoryId = (UUID) requestCategory.get(ID);
			/*
			 * 2. Based on the requestCategory, we need to query for layout associated
			 */
			Map<String, Object> requestCategoryLayout = requestCategoryRepo.loadLayoutByRequestCategory(requestCategoryId, type);
			if (Utils.checkCollectionHasKeyAndValue(requestCategoryLayout, ID)) {
				UUID layoutId = (UUID) requestCategoryLayout.get(ID);
				/*
				 * 3. Based on the layout, we need to query for elements associated with layout
				 * If elements, found directory then it will go to if condition, else we need to check for fieldSets mapped
				 */
				Set<Map<String, Object>> layoutElements = requestCategoryRepo.loadElementsByLayout(layoutId);
				if (layoutElements != null && !layoutElements.isEmpty()) {
					populateMatSelectElements(layoutElements, request);
					requestCategoryLayout.put(CommonConstants.ELEMENTS, layoutElements);
					requestCategory.put("layout", requestCategoryLayout);
				} else if (layoutElements != null && layoutElements.isEmpty()) {
					/*
					 * 4. We need to query for fieldSets, based the query response, we need to iterate over each fieldSet
					 * and load individual fieldSet elements. 
					 */
					Set<Map<String, Object>> layoutFieldSets = requestCategoryRepo.loadFieldSetsByLayout(layoutId, type);
					for (Map<String, Object> fieldSet : layoutFieldSets) {
						UUID fieldSetId = Utils.checkCollectionHasKeyAndValue(fieldSet, ID) ? (UUID) fieldSet.get(ID) : null;
						Set<Map<String, Object>> fieldSetElements = requestCategoryRepo
								.loadElementsByFieldSet(fieldSetId);
						if (fieldSetElements != null && !fieldSetElements.isEmpty()) {
							populateMatSelectElements(fieldSetElements, request);
							fieldSet.put(CommonConstants.ELEMENTS, fieldSetElements);
						} else {
							fieldSet.put(CommonConstants.ELEMENTS, new ArrayList<>());
						}
					}
					requestCategoryLayout.put(FIELD_SETS, layoutFieldSets);
					requestCategory.put(LAYOUT, requestCategoryLayout);
				}
				populateLayoutForModifyScenario(requestCategory, modifyEntityId, request, type);
			}
		}
		return requestCategory;
	}

	@SuppressWarnings("unchecked")
	private void populateMatSelectElements(Set<Map<String, Object>> elements, HttpServletRequest request) {
		elements.forEach(element -> {
			boolean isDependent = Utils.checkCollectionHasKeyAndValue(element, "isDependent") ? (Boolean)element.get("isDependent") : false;
			String controlType = Utils.checkCollectionHasKeyAndValue(element, "controlType") ? (String)element.get("controlType") : null;
			String apiQuery = Utils.checkCollectionHasKeyAndValue(element, "apiQuery") ? (String)element.get("apiQuery") : null;
			String name = Utils.checkCollectionHasKeyAndValue(element, "name") ? (String)element.get("name") : null;
			if(!isDependent && controlType!=null && controlType.equals(CommonConstants.FORM_FIELD_MAT_SELECT) && apiQuery!=null) {
				String apiToHit = Utils.getHttpRequestUriInformation(request) + apiQuery;
				HttpEntity<Object> entity = new HttpEntity<>(Utils.getHttpRequestHeadersInformation(request));
				try {
					Object result = restTemplate.exchange(apiToHit, HttpMethod.GET, entity, Object.class).getBody();
					Map<String, Object> response = (Map<String, Object>) result; 
					element.put(CommonConstants.OPTIONS, response.get("data"));
				} catch (Exception e) {
					element.put(CommonConstants.OPTIONS, new ArrayList<>());
				}
			}
			if(name.equalsIgnoreCase("gender")) {
				element.put(CommonConstants.OPTIONS, populateGenderDropDown());
			}
			element.put("value", "");
		});
	}
	
	private List<Map<String, String>> populateGenderDropDown() {
		List<Map<String, String>> genderOption = new ArrayList<>();
		Map<String, String> maleOption = new HashMap<>();
		maleOption.put("Male", "Male");
		Map<String, String> femaleOption = new HashMap<>();
		femaleOption.put("Female", "Female");
		genderOption.add(maleOption);
		genderOption.add(femaleOption);
		return genderOption;
	}

	@SuppressWarnings("unchecked")
	private void populateLayoutForModifyScenario(Map<String, Object> requestCategory, String modifyEntityId, HttpServletRequest request, String type) {
		if(Utils.checkCollectionHasKeyAndValue(requestCategory, "queryService") && modifyEntityId!=null && !modifyEntityId.isEmpty() && type.equalsIgnoreCase(CommonConstants.REQ_CAT_MODIFY)) {
			String apiToHit = Utils.getHttpRequestUriInformation(request) + (String)requestCategory.get("queryService") + modifyEntityId;
			HttpEntity<Object> entity = new HttpEntity<>(Utils.getHttpRequestHeadersInformation(request));
			try {
				Object result = restTemplate.exchange(apiToHit, HttpMethod.GET, entity, Object.class).getBody();
				Map<String, Object> response = (Map<String, Object>) result;
				if(Utils.checkCollectionHasKeyAndValue(response, "data")) {
					Map<String, Object> data = (Map<String, Object>) response.get("data");
					if(Utils.checkCollectionHasKeyAndValue(data, "results")) {
						List<Map<String, Object>> results = (List<Map<String, Object>>) data.get("results");
						for(Map<String, Object> map: results) {
							reqCatElements(requestCategory, map);
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void reqCatElements(Map<String, Object> requestCategory, Map<String, Object> dataObj) {
		if(Utils.checkCollectionHasKeyAndValue(requestCategory, LAYOUT)) {
			Map<String, Object> layout = (Map<String, Object>) requestCategory.get(LAYOUT);
			if(Utils.checkCollectionHasKeyAndValue(layout, ELEMENTS)) {
				Set<Map<String, Object>> elements =  (Set<Map<String, Object>>) layout.get(ELEMENTS);
				populateElementValues(elements, dataObj);
			} else if(Utils.checkCollectionHasKeyAndValue(layout, FIELD_SETS)) {
				List<Map<String, Object>> fieldSets =  (List<Map<String, Object>>) layout.get(FIELD_SETS);
				for(Map<String, Object> fieldSet: fieldSets) {
					Set<Map<String, Object>> elements =  (Set<Map<String, Object>>) fieldSet.get(ELEMENTS);
					populateElementValues(elements, dataObj);
				}
			}
		}
	}
	
	private void populateElementValues(Set<Map<String, Object>> elements, Map<String, Object> dataObj){
		for(Map<String, Object> map: elements) {
			if(dataObj.containsKey(map.get("name"))) {
				map.put("value", dataObj.get(map.get("name")));
			}
		}
	}
	
}
