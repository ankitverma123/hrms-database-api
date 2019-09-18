package com.exalink.hrmsdatabaseapi.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.CommonConstants;
import com.exalink.hrmsdatabaseapi.Utils;
import com.exalink.hrmsdatabaseapi.model.ChartRequestModel;
import com.exalink.hrmsdatabaseapi.service.IDashboardService;
import com.exalink.hrmsdatabaseapi.service.IEchartService;

/**
 * @author ankitkverma
 *
 */
@Service
public class DashboardServiceImpl implements IDashboardService {

	@Autowired
	private IEchartService echartService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String COUNT_IN_PERCENTAGE = ", CAST(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER() AS DECIMAL(18, 0)) as value";
	private static final String COUNT_IN_INTEGER = ", COUNT(*) AS value";
	private static final String FROM_CANDIDATE = " FROM candidate c";
	private static final String GROUP_BY_LABEL = " GROUP BY label";
	
	@Override
	public Object topSourcesVisualisation(ChartRequestModel crb) throws BaseException {
		String chartType = crb.getChartType();
		Map<String, Object> filters = crb.getFilters();
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT s.name as label");
		if(crb.isPercentageRequested())
			sb.append(COUNT_IN_PERCENTAGE);
		else
			sb.append(COUNT_IN_INTEGER);
		sb.append(FROM_CANDIDATE);
		sb.append(" JOIN candidate_sources s ON c.source=s.id");
		if (filters != null) {
			String joinQuery = filterCriteriaPopulation(filters, CommonConstants.JOIN);
			if(joinQuery!=null && !joinQuery.isEmpty())
				sb.append(joinQuery);
		}
		sb.append(" WHERE s.is_active IS TRUE");
		if (filters != null) {
			String whereClauseQuery = filterCriteriaPopulation(filters, CommonConstants.WHERECLAUSE);
			if(whereClauseQuery!=null && !whereClauseQuery.isEmpty())
				if(sb.toString().contains(CommonConstants.WHERE))
					sb.append(CommonConstants._AND_);
				else
					sb.append(CommonConstants._WHERE_);
				sb.append(whereClauseQuery);
		}
		sb.append(GROUP_BY_LABEL);
		
		List<Map<String, Object>> queryResponse= jdbcTemplate.queryForList(sb.toString());
		
		if (chartType.equalsIgnoreCase(CommonConstants.BAR_CHART)) {
			return echartService.convertToBarChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.PIE_CHART)) {
			return echartService.convertToPieChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.DOUGHNUT_CHART)) {
			return echartService.convertToDoughnutChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.SIMPLE_PIE_CHART)) {
			return echartService.convertToSimplePieChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.HORIZONTAL_BAR_CHART)) {
			return echartService.convertToHorizontalBarChart(queryResponse, crb);
		}
		return queryResponse;
	}

	@Override
	public Object recruitmentStatusVisualisation(ChartRequestModel crb) throws BaseException {
		String chartType = crb.getChartType();
		Map<String, Object> filters = crb.getFilters();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT s.status as label");
		if(crb.isPercentageRequested())
			sb.append(COUNT_IN_PERCENTAGE);
		else
			sb.append(COUNT_IN_INTEGER);
		sb.append(FROM_CANDIDATE);
		sb.append(" JOIN onboard_status s ON c.onboard_status=s.id ");
		if (filters != null) {
			String joinQuery = filterCriteriaPopulation(filters, CommonConstants.JOIN);
			if(joinQuery!=null && !joinQuery.isEmpty())
				sb.append(joinQuery);
		}
		if (filters != null) {
			String whereClauseQuery = filterCriteriaPopulation(filters, CommonConstants.WHERECLAUSE);
			if(whereClauseQuery!=null && !whereClauseQuery.isEmpty()) {
				if(sb.toString().contains(CommonConstants.WHERE))
					sb.append(CommonConstants._AND_);
				else
					sb.append(CommonConstants._WHERE_);
				sb.append(whereClauseQuery);
			}
		}
		sb.append(GROUP_BY_LABEL);
		
		List<Map<String, Object>> queryResponse= jdbcTemplate.queryForList(sb.toString());
		if (chartType.equalsIgnoreCase(CommonConstants.BAR_CHART)) {
			return echartService.convertToBarChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.PIE_CHART)) {
			return echartService.convertToPieChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.DOUGHNUT_CHART)) {
			return echartService.convertToDoughnutChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.SIMPLE_PIE_CHART)) {
			return echartService.convertToSimplePieChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.HORIZONTAL_BAR_CHART)) {
			return echartService.convertToHorizontalBarChart(queryResponse, crb);
		}
		return queryResponse;
	}

	@Override
	public Object genderMixtureVisualisation(ChartRequestModel crb) throws BaseException {
		String chartType = crb.getChartType();
		Map<String, Object> filters = crb.getFilters();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT c.gender as label");
		if(crb.isPercentageRequested())
			sb.append(COUNT_IN_PERCENTAGE);
		else
			sb.append(COUNT_IN_INTEGER);
		sb.append(FROM_CANDIDATE);
		if (filters != null) {
			String joinQuery = filterCriteriaPopulation(filters, CommonConstants.JOIN);
			if(joinQuery!=null && !joinQuery.isEmpty())
				sb.append(joinQuery);
		}
		if (filters != null) {
			String whereClauseQuery = filterCriteriaPopulation(filters, CommonConstants.WHERECLAUSE);
			if(whereClauseQuery!=null && !whereClauseQuery.isEmpty()) {
				if(sb.toString().contains(CommonConstants.WHERE))
					sb.append(CommonConstants._AND_);
				else
					sb.append(CommonConstants._WHERE_);
				sb.append(whereClauseQuery);
			}
		}
		sb.append(GROUP_BY_LABEL);
		
		List<Map<String, Object>> queryResponse= jdbcTemplate.queryForList(sb.toString());
		if (chartType.equalsIgnoreCase(CommonConstants.BAR_CHART)) {
			return echartService.convertToBarChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.PIE_CHART)) {
			return echartService.convertToPieChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.DOUGHNUT_CHART)) {
			return echartService.convertToDoughnutChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.SIMPLE_PIE_CHART)) {
			return echartService.convertToSimplePieChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.HORIZONTAL_BAR_CHART)) {
			return echartService.convertToHorizontalBarChart(queryResponse, crb);
		}
		return queryResponse;
	}

	@Override
	public Object offerDeclineVisualisation(ChartRequestModel crb) throws BaseException {
		String chartType = crb.getChartType();
		Map<String, Object> filters = crb.getFilters();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT cdc.category as label");
		if(crb.isPercentageRequested())
			sb.append(COUNT_IN_PERCENTAGE);
		else
			sb.append(COUNT_IN_INTEGER);
		sb.append(FROM_CANDIDATE);
		sb.append(" JOIN candidate_offer co ON c.candidate_offer_status=co.id");
		sb.append(" JOIN offer_status os ON co.status=os.id");
		sb.append(" JOIN offer_decline_category cdc ON co.decline_category=cdc.id");
		if (filters != null) {
			String joinQuery = filterCriteriaPopulation(filters, CommonConstants.JOIN);
			if(joinQuery!=null && !joinQuery.isEmpty())
				sb.append(joinQuery);
		}
		if (filters != null) {
			String whereClauseQuery = filterCriteriaPopulation(filters, CommonConstants.WHERECLAUSE);
			if(whereClauseQuery!=null && !whereClauseQuery.isEmpty()) {
				if(sb.toString().contains(CommonConstants.WHERE))
					sb.append(CommonConstants._AND_);
				else
					sb.append(CommonConstants._WHERE_);
				sb.append(whereClauseQuery);
			}
		}
		sb.append(GROUP_BY_LABEL);
		
		List<Map<String, Object>> queryResponse= jdbcTemplate.queryForList(sb.toString());
		if (chartType.equalsIgnoreCase(CommonConstants.BAR_CHART)) {
			return echartService.convertToBarChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.PIE_CHART)) {
			return echartService.convertToPieChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.DOUGHNUT_CHART)) {
			return echartService.convertToDoughnutChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.SIMPLE_PIE_CHART)) {
			return echartService.convertToSimplePieChart(queryResponse, crb);
		} else if (chartType.equalsIgnoreCase(CommonConstants.HORIZONTAL_BAR_CHART)) {
			return echartService.convertToHorizontalBarChart(queryResponse, crb);
		}
		return queryResponse;
	}

	private String filterCriteriaPopulation(Map<String, Object> filters, String requestedString) {
		ArrayList<String> joinClauses = new ArrayList<>();
		ArrayList<String> whereClauses = new ArrayList<>();
		if (Utils.checkCollectionHasKeyAndValueForFilters(filters, CommonConstants.FILTER_BY_FINANCIAL_YEAR)) {
			if (requestedString.equals(CommonConstants.JOIN))
				joinClauses.add(" JOIN financial_year fy ON c.financial_year=fy.id");
			else if (requestedString.equals(CommonConstants.WHERECLAUSE))
				whereClauses.add("fy.id='" + filters.get(CommonConstants.FILTER_BY_FINANCIAL_YEAR) + "'");
		}
		
		if (Utils.checkCollectionHasKeyAndValueForFilters(filters, CommonConstants.FILTER_BY_MARKET_OFFERING)) {
			if (requestedString.equals(CommonConstants.JOIN))
				joinClauses.add(" JOIN market_subbusinessline mbl ON c.market_business_line=mbl.id");
			else if (requestedString.equals(CommonConstants.WHERECLAUSE))
				whereClauses.add("mbl.market_offering='" + filters.get(CommonConstants.FILTER_BY_MARKET_OFFERING) + "'");
		}
		
		if(requestedString.equals(CommonConstants.JOIN))
			return String.join(" ", joinClauses);
		
		if(requestedString.equals(CommonConstants.WHERECLAUSE))
			return String.join(CommonConstants._AND_, whereClauses);
		
		return null;
	}

}
