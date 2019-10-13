package com.exalink.hrmsdatabaseapi.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exalink.hrmsdatabaseapi.service.ISubCompetencyService;
import com.exalink.hrmsfabric.common.BaseException;
import com.exalink.hrmsfabric.common.CommonConstants;
import com.exalink.hrmsfabric.common.ResponseData;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/competency")
public class ComptencyController {
	
	@Autowired
	private ISubCompetencyService subCompetencyService;
	
	@GetMapping(value="/subCompetency/byCompetency/{competency}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData listSubBusinessLine(@PathVariable("competency") String competency, @RequestParam(required = false) boolean requestForDropDown) throws BaseException{
		return new ResponseData(subCompetencyService.listSubCompetency(UUID.fromString(competency), requestForDropDown), CommonConstants.SUCCESS, HttpStatus.OK, null);
	}
	
}