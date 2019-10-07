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
	public Object listFinancialYear(Integer skip, Integer top, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listCandidateSources(Integer skip, Integer top, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listOnboardStatus(Integer skip, Integer top, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listOfferDeclineCategories(Integer skip, Integer top, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listMarketOffering(Integer skip, Integer top, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listCompetency(Integer skip, Integer top, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listCandidateOfferStatus(Integer skip, Integer top, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listFileTracking(Integer skip, Integer top, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	
	public Object persist(String path, Map<String, Object> requestMap) throws BaseException;
}
