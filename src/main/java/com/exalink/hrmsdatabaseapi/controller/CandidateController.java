package com.exalink.hrmsdatabaseapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exalink.hrmsdatabaseapi.service.ICandidateService;
import com.exalink.hrmsfabric.common.BaseException;
import com.exalink.hrmsfabric.common.ResponseData;
import com.exalink.hrmsfabric.common.Utils;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/candidate")
public class CandidateController {
	
	@Autowired
	private ICandidateService candidateService;
	
	@PutMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData saveCandidate(@RequestBody Map<String, Object> candidateRequestMap) throws BaseException{
		return new ResponseData(candidateService.saveCandidate(candidateRequestMap), "Candidate Created Succesfully", HttpStatus.OK, null);
	}
	
	@PutMapping(value="/batch", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData saveCandidate(@RequestBody List<Map<String, Object>> candidateRequestMap) throws BaseException {
		return new ResponseData(candidateService.saveCandidate(candidateRequestMap), null, HttpStatus.OK, null);
	}
	
	@PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData updateCandidate(@RequestBody Map<String, Object> candidateRequestMap) throws BaseException {
		return new ResponseData(candidateService.updateCandidate(candidateRequestMap), "Candidate Updated Succesfully", HttpStatus.OK, null);
	}
	
	@GetMapping(value="/", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData listCandidate(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String filter) throws BaseException {
		return new ResponseData(candidateService.listCandidates(pageNumber, pageSize, sortField, sortDirection, filter), null, HttpStatus.OK, null);
	}
	
	@PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseData uploadFile(@RequestParam("file") MultipartFile file) throws IOException, BaseException {
		return new ResponseData(candidateService.saveCandidate(file), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/offerStatus/{candidateId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData candidateofferStatus(@PathVariable String candidateId) throws BaseException{
		if(candidateId == null || candidateId.isEmpty() || !Utils.checkIfUUID(candidateId))
			throw new BaseException(CandidateController.class, "Invalid candidateId Specified in URL");
		return new ResponseData(candidateService.candidateOfferStatus(UUID.fromString(candidateId)), "Candidate Offer Status", HttpStatus.OK, null);
	}
}
