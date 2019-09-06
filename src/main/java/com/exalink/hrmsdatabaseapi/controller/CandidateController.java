package com.exalink.hrmsdatabaseapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.entity.candidate.Candidate;
import com.exalink.hrmsdatabaseapi.service.ICandidateService;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/hrms_database/candidate")
public class CandidateController {
	
	@Autowired
	private ICandidateService candidateService;
	
	@PutMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Candidate saveCandidate(@RequestBody Map<String, Object> candidateRequestMap) throws BaseException{
		return candidateService.saveCandidate(candidateRequestMap);
	}
	
}
