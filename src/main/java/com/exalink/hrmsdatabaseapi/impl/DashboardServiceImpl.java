package com.exalink.hrmsdatabaseapi.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.model.ChartRequestModel;
import com.exalink.hrmsdatabaseapi.repository.ICandidateSourceRepository;
import com.exalink.hrmsdatabaseapi.service.IDashboardService;
import com.exalink.hrmsdatabaseapi.service.IEchartService;

/**
 * @author ankitkverma
 *
 */
@Service
public class DashboardServiceImpl implements IDashboardService {

	@Autowired
	private ICandidateSourceRepository candidateSourceRepository;

	@Autowired
	private IEchartService echartService;

	@Override
	public Object topSourcesVisualisation(ChartRequestModel crb) throws BaseException {
		String chartType = crb.getChartType();
		List<Map<String, Object>> queryResponse = candidateSourceRepository.candidateSourcesInPercentage();
		if (chartType.equalsIgnoreCase("bar")) {
			return echartService.convertToBarChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase("pie")) {
			return echartService.convertToPieChart(queryResponse, crb);
		}else if (chartType.equalsIgnoreCase("doughnut")) {
			return echartService.convertToDoughnutChart(queryResponse, crb);
		}else if (chartType.equalsIgnoreCase("simplepie")) {
			return echartService.convertToSimplePieChart(queryResponse, crb);
		}else if (chartType.equalsIgnoreCase("horizontalbar")) {
			return echartService.convertToHorizontalBarChart(queryResponse, crb);
		}
		return queryResponse;
	}

	@Override
	public Object recruitmentStatusVisualisation(ChartRequestModel crb) throws BaseException {
		String chartType = crb.getChartType();
		List<Map<String, Object>> queryResponse;
		if(crb.isPercentageRequested()) {
			queryResponse = candidateSourceRepository.recruitmentStatusInPercentage();
		}else {
			queryResponse = candidateSourceRepository.recruitmentStatusInNumbers();
		}
		if (chartType.equalsIgnoreCase("bar")) {
			return echartService.convertToBarChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase("pie")) {
			return echartService.convertToPieChart(queryResponse, crb);
		}else if (chartType.equalsIgnoreCase("doughnut")) {
			return echartService.convertToDoughnutChart(queryResponse, crb);
		}else if (chartType.equalsIgnoreCase("simplepie")) {
			return echartService.convertToSimplePieChart(queryResponse, crb);
		}else if (chartType.equalsIgnoreCase("horizontalbar")) {
			return echartService.convertToHorizontalBarChart(queryResponse, crb);
		}
		return queryResponse;
	}

}
