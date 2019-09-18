package com.exalink.hrmsdatabaseapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.ResponseData;
import com.exalink.hrmsdatabaseapi.service.IFinancialYearService;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/hrms_database/financial")
public class FinancialYearController {
	
	@Autowired
	private IFinancialYearService financialYearService;
		
	@GetMapping(value="/", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData listAllFinancialYear(@RequestParam Integer $top, @RequestParam Integer $skip, @RequestParam(required = false) String sortDirection, 
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String $filter) throws BaseException {
		return new ResponseData(financialYearService.list($skip, $top, sortField, sortDirection, $filter), null, HttpStatus.OK, null);
	}
}
