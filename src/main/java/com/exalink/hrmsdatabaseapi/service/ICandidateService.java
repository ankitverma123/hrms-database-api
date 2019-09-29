package com.exalink.hrmsdatabaseapi.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.entity.candidate.Candidate;

/**
 * @author ankitkverma
 *
 */
@Service
public interface ICandidateService {
	
	public Candidate saveCandidate(Map<String, Object> candidateRequestMap) throws BaseException;
	public List<String> saveCandidate(List<Map<String, Object>> candidateRequestMap) throws BaseException;
	public Candidate updateCandidate(Map<String, Object> candidateRequestMap) throws BaseException;
	public Object saveCandidate(MultipartFile file)throws IOException, BaseException;
	
	public Map<String, Object> listCandidates(Integer $skip, Integer $top, String sortField, String sortDirection, String $filter) throws BaseException;
}
