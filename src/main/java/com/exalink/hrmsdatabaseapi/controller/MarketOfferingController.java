package com.exalink.hrmsdatabaseapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.CommonConstants;
import com.exalink.hrmsdatabaseapi.ResponseData;
import com.exalink.hrmsdatabaseapi.service.IMarketOfferingService;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/offering")
public class MarketOfferingController {

	@Autowired
	private IMarketOfferingService marketOfferingService;
	
	@GetMapping(value="/{path}/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData listAll(@PathVariable("path") String path) throws BaseException{
		if(path.equalsIgnoreCase(CommonConstants.MARKET_OFFERING))
			return new ResponseData(marketOfferingService.listAllMarketOffering(), CommonConstants.SUCCESS, HttpStatus.OK, null);
		else if (path.equalsIgnoreCase(CommonConstants.SUB_BUSINESS_LINE))
			return new ResponseData(marketOfferingService.listAllSubBusinessLine(), CommonConstants.SUCCESS, HttpStatus.OK, null);
		return new ResponseData(null, CommonConstants.FAILURE, HttpStatus.BAD_REQUEST, null);
	}
	
	
	@GetMapping(value="/subBusinessLine/byMarketOffering/{marketOffering}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData listSubBusinessLine(@PathVariable("marketOffering") String marketOffering, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(marketOfferingService.listSubBusinesslineByMarketOffering(Long.valueOf(marketOffering), requestForDropDown), CommonConstants.SUCCESS, HttpStatus.OK, null);
	}
	
}
