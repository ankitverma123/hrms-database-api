package com.exalink.hrmsdatabaseapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.ResponseData;
import com.exalink.hrmsdatabaseapi.service.ISQLService;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/hrms_database/sql")
public class SQLController {
	
	@Autowired
	private ISQLService sqlService;
	
	@GetMapping(value="/financialYear/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData financialYear(@RequestParam Integer $top, @RequestParam Integer $skip, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String $filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listFinancialYear($skip, $top, sortField, sortDirection, $filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/candidateSources/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData candidateSources(@RequestParam Integer $top, @RequestParam Integer $skip, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String $filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listCandidateSources($skip, $top, sortField, sortDirection, $filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/onboardStatus/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData onboardStatus(@RequestParam Integer $top, @RequestParam Integer $skip, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String $filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listOnboardStatus($skip, $top, sortField, sortDirection, $filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/offerDeclineCategory/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData offerDeclineCategory(@RequestParam Integer $top, @RequestParam Integer $skip, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String $filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listOfferDeclineCategories($skip, $top, sortField, sortDirection, $filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/marketOffering/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData marketOffering(@RequestParam Integer $top, @RequestParam Integer $skip, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String $filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listMarketOffering($skip, $top, sortField, sortDirection, $filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@GetMapping(value="/competency/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData competency(@RequestParam Integer $top, @RequestParam Integer $skip, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String $filter, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(sqlService.listMarketOffering($skip, $top, sortField, sortDirection, $filter, requestForDropDown), null, HttpStatus.OK, null);
	}
	
	@PutMapping(value="/{path}/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData persist(@PathVariable String path, @RequestBody Map<String, Object> requestMap) throws BaseException{
		return new ResponseData(sqlService.persist(path, requestMap), "Request accepted successfully", HttpStatus.OK, null);
	}
}
