package com.exalink.hrmsdatabaseapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exalink.hrmsdatabaseapi.ResponseData;
import com.exalink.hrmsdatabaseapi.service.IRequestCategoryService;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/requestCategory")
public class RequestCategoryController {
	
	@Autowired
	private IRequestCategoryService requestCategoryService;
	
	@GetMapping(value="/", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData fetchClients(@RequestParam String name, @RequestParam String type,@RequestParam(required=false) String modifyEntityId, HttpServletRequest request){
		return new ResponseData(requestCategoryService.loadRequestCategory(name, type, modifyEntityId, request), null, HttpStatus.OK, null);
	}
	
}
