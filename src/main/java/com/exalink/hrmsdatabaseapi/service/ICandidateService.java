package com.exalink.hrmsdatabaseapi.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exalink.hrmsdatabaseapi.entity.candidate.Candidate;
import com.exalink.hrmsfabric.common.BaseException;

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
	public Object candidateOfferStatus(UUID candidateId) throws BaseException;
	
	public Map<String, Object> listCandidates(Integer pageNumber, Integer pageSize, String sortField, String sortDirection, String filter) throws BaseException;
}
