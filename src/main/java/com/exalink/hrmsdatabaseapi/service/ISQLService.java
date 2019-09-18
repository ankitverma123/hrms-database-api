package com.exalink.hrmsdatabaseapi.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.BaseException;

/**
 * @author ankitkverma
 *
 */
@Service
public interface ISQLService {
	public Map<String, Object> listFinancialYear(Integer skip, Integer top, String sortField, String sortDirection, String filter) throws BaseException;
	public Map<String, Object> listCandidateSources(Integer skip, Integer top, String sortField, String sortDirection, String filter) throws BaseException;
	public Map<String, Object> listOnboardStatus(Integer skip, Integer top, String sortField, String sortDirection, String filter) throws BaseException;
	public Map<String, Object> listOfferDeclineCategories(Integer skip, Integer top, String sortField, String sortDirection, String filter) throws BaseException;
	public Map<String, Object> listMarketOffering(Integer skip, Integer top, String sortField, String sortDirection, String filter) throws BaseException;
	
	public Object persist(String path, Map<String, Object> requestMap) throws BaseException;
}
