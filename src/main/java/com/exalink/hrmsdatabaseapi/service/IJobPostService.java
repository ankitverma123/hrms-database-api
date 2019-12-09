package com.exalink.hrmsdatabaseapi.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.exalink.hrmsfabric.common.BaseException;

/**
 * @author ankitkverma
 *
 */
@Service
public interface IJobPostService {
	Object save(Map<String, Object> requestMap) throws BaseException;
	Object update(Map<String, Object> requestMap) throws BaseException;
	Map<String, Object> queryService(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter) throws BaseException;
}
