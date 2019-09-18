package com.exalink.hrmsdatabaseapi.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.service.ICandidateSourceService;

/**
 * @author ankitkverma
 *
 */
@Service
public class CandidateSourcesImpl implements ICandidateSourceService{

	@Override
	public Map<String, Object> list(Integer skip, Integer top, String sortField, String sortDirection, String filter)
			throws BaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
