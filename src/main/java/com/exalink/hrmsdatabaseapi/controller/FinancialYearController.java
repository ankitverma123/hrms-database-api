package com.exalink.hrmsdatabaseapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.ResponseData;
import com.exalink.hrmsdatabaseapi.service.IFinancialYearService;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/financial")
public class FinancialYearController {
	
	@Autowired
	private IFinancialYearService financialYearService;
	
	@PostMapping(value="/update", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData update(@RequestBody Map<String, Object> requestMap) throws BaseException{
		return new ResponseData(financialYearService.updateFinancialYear(requestMap), "Financial year updated successfully", HttpStatus.OK, null);
	}
}
