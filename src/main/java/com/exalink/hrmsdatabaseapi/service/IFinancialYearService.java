package com.exalink.hrmsdatabaseapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.entity.candidate.FinancialYear;

/**
 * @author ankitkverma
 *
 */
@Service
public interface IFinancialYearService {
	public List<FinancialYear> listAll() throws BaseException;
	
	public Map<String, Object> list(Integer $skip, Integer $top, String sortField, String sortDirection, String $filter) throws BaseException;
}
