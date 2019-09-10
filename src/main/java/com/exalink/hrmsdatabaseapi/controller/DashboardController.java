package com.exalink.hrmsdatabaseapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.model.ChartRequestModel;
import com.exalink.hrmsdatabaseapi.service.IDashboardService;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/hrms_database/dashboard")
public class DashboardController {

	@Autowired
	private IDashboardService dashboardService;
	
	@GetMapping(value="/topSources", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Object topSources(@RequestBody ChartRequestModel crb) throws BaseException{
		return dashboardService.topSourcesVisualisation(crb);
	}
	
	@GetMapping(value="/recruitmentStatus", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Object recruitmentStatus(@RequestBody ChartRequestModel crb) throws BaseException{
		return dashboardService.recruitmentStatusVisualisation(crb);
	}
}
