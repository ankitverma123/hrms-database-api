package com.exalink.hrmsdatabaseapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exalink.hrmsdatabaseapi.service.ISQLService;
import com.exalink.hrmsfabric.common.BaseException;
import com.exalink.hrmsfabric.common.ResponseData;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/sql")
public class SQLController {
	
	@Autowired
	private ISQLService sqlService;
	
	@GetMapping(value="/financialYear/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData financialYear(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listFinancialYear(pageNumber, pageSize, sortField, sortDirection, filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/candidateSources/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData candidateSources(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listCandidateSources(pageNumber, pageSize, sortField, sortDirection, filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/onboardStatus/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData onboardStatus(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listOnboardStatus(pageNumber, pageSize, sortField, sortDirection, filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/offerDeclineCategory/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData offerDeclineCategory(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listOfferDeclineCategories(pageNumber, pageSize, sortField, sortDirection, filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/marketOffering/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData marketOffering(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listMarketOffering(pageNumber, pageSize, sortField, sortDirection, filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/competency/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData competency(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listCompetency(pageNumber, pageSize, sortField, sortDirection, filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/fileTracking/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData fileTrackingList(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listFileTracking(pageNumber, pageSize, sortField, sortDirection, filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/candidateOfferStatus/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData candidateOfferStatusList(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listCandidateOfferStatus(pageNumber, pageSize, sortField, sortDirection, filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@PutMapping(value="/{path}/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData persist(@PathVariable String path, @RequestBody Map<String, Object> requestMap) throws BaseException{
		return new ResponseData(sqlService.persist(path, requestMap), path.toUpperCase()+ " created successfully", HttpStatus.OK, null);
	}
	
	@PostMapping(value="/{path}/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData updateConfigurations(@PathVariable String path, @RequestBody Map<String, Object> requestMap) throws BaseException{
		return new ResponseData(sqlService.update(path, requestMap), path.toUpperCase()+" updated successfully", HttpStatus.OK, null);
	}
	
	@DeleteMapping(value="/{path}/{uuid}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData deleteEntity(@PathVariable String path, @PathVariable String uuid) throws BaseException{
		return new ResponseData(sqlService.delete(path, uuid), path.toUpperCase()+" deleted successfully", HttpStatus.OK, null);
	}
}
