package com.exalink.hrmsdatabaseapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exalink.hrmsdatabaseapi.service.IJobPostService;
import com.exalink.hrmsfabric.common.BaseException;
import com.exalink.hrmsfabric.common.ResponseData;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/jobPost")
public class JobPostController {
	
	@Autowired
	private IJobPostService jobPostService;
	
	@PutMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData save(@RequestBody Map<String, Object> requestMap) throws BaseException{
		return new ResponseData(jobPostService.save(requestMap), "New job post request created successfully", HttpStatus.OK, null);
	}
	
	@PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData update(@RequestBody Map<String, Object> requestMap) throws BaseException{
		return new ResponseData(jobPostService.update(requestMap), "Job post request updated successfully", HttpStatus.OK, null);
	}
	
	@GetMapping(value="/", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData listCandidate(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String filter) throws BaseException {
		return new ResponseData(jobPostService.queryService(pageNumber, pageSize, sortField, sortDirection, filter), null, HttpStatus.OK, null);
	}
	
}
