package com.exalink.hrmsdatabaseapi.service;

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
	FinancialYear updateFinancialYear(Map<String, Object> request) throws BaseException;
}