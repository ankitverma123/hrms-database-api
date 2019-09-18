package com.exalink.hrmsdatabaseapi.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.BaseException;

/**
 * @author ankitkverma
 *
 */
@Service
public interface ICandidateSourceService {
	public Map<String, Object> list(Integer skip, Integer top, String sortField, String sortDirection, String filter) throws BaseException;
}
