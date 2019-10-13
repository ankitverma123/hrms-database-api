package com.exalink.hrmsdatabaseapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exalink.hrmsdatabaseapi.model.ChartRequestModel;
import com.exalink.hrmsdatabaseapi.service.IDashboardService;
import com.exalink.hrmsfabric.common.BaseException;
import com.exalink.hrmsfabric.common.ResponseData;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private IDashboardService dashboardService;
	
	@PostMapping(value="/topSources", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData topSources(@RequestBody ChartRequestModel crb) throws BaseException{
		return new ResponseData(dashboardService.topSourcesVisualisation(crb), null, HttpStatus.OK, null);
	}
	
	@PostMapping(value="/recruitmentStatus", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData recruitmentStatus(@RequestBody ChartRequestModel crb) throws BaseException{
		return new ResponseData(dashboardService.recruitmentStatusVisualisation(crb), null, HttpStatus.OK, null);
	}
	
	@PostMapping(value="/genderMixture", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData genderMixVisualisation(@RequestBody ChartRequestModel crb) throws BaseException{
		return new ResponseData(dashboardService.genderMixtureVisualisation(crb), null, HttpStatus.OK, null);
	}
	
	@PostMapping(value="/declineReasons", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData declineReasonsVisualisation(@RequestBody ChartRequestModel crb) throws BaseException{
		return new ResponseData(dashboardService.offerDeclineVisualisation(crb), null, HttpStatus.OK, null);
	}
	
}
