package com.exalink.hrmsdatabaseapi.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.exalink.hrmsfabric.common.BaseException;

/**
 * @author ankitkverma
 *
 */
@Service
public interface ISQLService {
	public Object listFinancialYear(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listCandidateSources(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listOnboardStatus(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listOfferDeclineCategories(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listMarketOffering(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listCompetency(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listCandidateOfferStatus(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	public Object listFileTracking(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter, boolean requestForDropDown) throws BaseException;
	
	public Object persist(String path, Map<String, Object> requestMap) throws BaseException;
	public Object update(String path, Map<String, Object> requestMap) throws BaseException;
	public Object delete(String path, String uuid) throws BaseException;
}
