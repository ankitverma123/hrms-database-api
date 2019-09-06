package com.exalink.hrmsdatabaseapi.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.entity.candidate.Candidate;

/**
 * @author ankitkverma
 *
 */
@Service
public interface ICandidateService {
	
	public Candidate saveCandidate(Map<String, Object> candidateRequestMap) throws BaseException;
	public Candidate updateCandidate(Map<String, Object> candidateRequestMap) throws BaseException;
	
}
