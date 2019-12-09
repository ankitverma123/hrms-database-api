package com.exalink.hrmsdatabaseapi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.model.ChartRequestModel;
import com.exalink.hrmsdatabaseapi.service.IDashboardService;
import com.exalink.hrmsdatabaseapi.service.IEchartService;
import com.exalink.hrmsfabric.common.BaseException;
import com.exalink.hrmsfabric.common.CommonConstants;
import com.exalink.hrmsfabric.common.Utils;

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

	private static final Logger logger = LogManager.getLogger(DashboardServiceImpl.class);
	private static final String CLASSNAME = DashboardServiceImpl.class.getName();
	
	@Override
	public Object topSourcesVisualisation(ChartRequestModel crb) throws BaseException {
		logger.debug(CLASSNAME + " >> topSourcesVisualisation() >> START");
		String chartType = crb.getChartType();
		Map<String, Object> filters = crb.getFilters();
		Map<String, Object> detailedAnalysis = detailedAnalysisRequested(crb);
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT s.candidate_source as label");
		if (crb.isPercentageRequested())
			sb.append(COUNT_IN_PERCENTAGE);
		else
			sb.append(COUNT_IN_INTEGER);
		if (detailedAnalysis != null && Utils.checkCollectionHasKeyAndValue(detailedAnalysis, CommonConstants.QUERY)) {
			sb.append(", " + detailedAnalysis.get(CommonConstants.QUERY).toString());
		}
		sb.append(FROM_CANDIDATE);
		sb.append(" JOIN candidate_sources s ON c.candidate_source=s.id");
		if (detailedAnalysis != null && Utils.checkCollectionHasKeyAndValue(detailedAnalysis, CommonConstants.JOIN)) {
			sb.append(detailedAnalysis.get(CommonConstants.JOIN).toString());
		}
		if (filters != null) {
			String joinQuery = filterCriteriaPopulation(filters, CommonConstants.JOIN);
			if (joinQuery != null && !joinQuery.isEmpty())
				sb.append(joinQuery);
		}
		sb.append(" WHERE s.is_active IS TRUE");
		if (filters != null) {
			String whereClauseQuery = filterCriteriaPopulation(filters, CommonConstants.WHERECLAUSE);
			if (whereClauseQuery != null && !whereClauseQuery.isEmpty())
				if (sb.toString().contains(CommonConstants.WHERE))
					sb.append(CommonConstants._AND_);
				else
					sb.append(CommonConstants._WHERE_);
			sb.append(whereClauseQuery);
		}
		sb.append(GROUP_BY_LABEL);
		if (detailedAnalysis != null
				&& Utils.checkCollectionHasKeyAndValue(detailedAnalysis, CommonConstants.GROUPBY)) {
			sb.append(", " + detailedAnalysis.get(CommonConstants.GROUPBY).toString());
		}
		logger.debug(CLASSNAME + " >> topSourcesVisualisation() >> Query ");
		logger.debug(sb.toString());
		List<Map<String, Object>> queryResponse = jdbcTemplate.queryForList(sb.toString());
		logger.debug(CLASSNAME + " >> topSourcesVisualisation() >> Query Response= "+queryResponse.size());
		
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
		} else if (chartType.equalsIgnoreCase(CommonConstants.MULTIPLE_COMPARISON_BARCHART)) {
			return echartService.convertToMultipleComparisonBarChart(queryResponse, crb);
		}
		return queryResponse;
	}

	@Override
	public Object recruitmentStatusVisualisation(ChartRequestModel crb) throws BaseException {
		logger.debug(CLASSNAME + " >> recruitmentStatusVisualisation() >> START");
		String chartType = crb.getChartType();
		Map<String, Object> filters = crb.getFilters();
		Map<String, Object> detailedAnalysis = detailedAnalysisRequested(crb);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT s.onboard_status as label");
		if (crb.isPercentageRequested())
			sb.append(COUNT_IN_PERCENTAGE);
		else
			sb.append(COUNT_IN_INTEGER);
		if (detailedAnalysis != null && Utils.checkCollectionHasKeyAndValue(detailedAnalysis, CommonConstants.QUERY)) {
			sb.append(", " + detailedAnalysis.get(CommonConstants.QUERY).toString());
		}
		sb.append(FROM_CANDIDATE);
		sb.append(" JOIN onboard_status s ON c.onboard_status=s.id ");
		if (detailedAnalysis != null && Utils.checkCollectionHasKeyAndValue(detailedAnalysis, CommonConstants.JOIN)) {
			sb.append(detailedAnalysis.get(CommonConstants.JOIN).toString());
		}
		if (filters != null) {
			String joinQuery = filterCriteriaPopulation(filters, CommonConstants.JOIN);
			if (joinQuery != null && !joinQuery.isEmpty())
				sb.append(joinQuery);
		}
		if (filters != null) {
			String whereClauseQuery = filterCriteriaPopulation(filters, CommonConstants.WHERECLAUSE);
			if (whereClauseQuery != null && !whereClauseQuery.isEmpty()) {
				if (sb.toString().contains(CommonConstants.WHERE))
					sb.append(CommonConstants._AND_);
				else
					sb.append(CommonConstants._WHERE_);
				sb.append(whereClauseQuery);
			}
		}
		sb.append(GROUP_BY_LABEL);
		if (detailedAnalysis != null
				&& Utils.checkCollectionHasKeyAndValue(detailedAnalysis, CommonConstants.GROUPBY)) {
			sb.append(", " + detailedAnalysis.get(CommonConstants.GROUPBY).toString());
		}

		logger.debug(CLASSNAME + " >> recruitmentStatusVisualisation() >> Query ");
		logger.debug(sb.toString());
		List<Map<String, Object>> queryResponse = jdbcTemplate.queryForList(sb.toString());
		logger.debug(CLASSNAME + " >> recruitmentStatusVisualisation() >> Query Response= "+queryResponse.size());
		
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
		} else if (chartType.equalsIgnoreCase(CommonConstants.MULTIPLE_COMPARISON_BARCHART)) {
			return echartService.convertToMultipleComparisonBarChart(queryResponse, crb);
		}
		return queryResponse;
	}

	@Override
	public Object genderMixtureVisualisation(ChartRequestModel crb) throws BaseException {
		logger.debug(CLASSNAME + " >> genderMixtureVisualisation() >> START");
		String chartType = crb.getChartType();
		Map<String, Object> filters = crb.getFilters();
		Map<String, Object> detailedAnalysis = detailedAnalysisRequested(crb);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT c.gender as label");
		if (crb.isPercentageRequested())
			sb.append(COUNT_IN_PERCENTAGE);
		else
			sb.append(COUNT_IN_INTEGER);
		if (detailedAnalysis != null && Utils.checkCollectionHasKeyAndValue(detailedAnalysis, CommonConstants.QUERY)) {
			sb.append(", " + detailedAnalysis.get(CommonConstants.QUERY).toString());
		}
		sb.append(FROM_CANDIDATE);
		if (detailedAnalysis != null && Utils.checkCollectionHasKeyAndValue(detailedAnalysis, CommonConstants.JOIN)) {
			sb.append(detailedAnalysis.get(CommonConstants.JOIN).toString());
		}
		if (filters != null) {
			String joinQuery = filterCriteriaPopulation(filters, CommonConstants.JOIN);
			if (joinQuery != null && !joinQuery.isEmpty())
				sb.append(joinQuery);
		}
		if (filters != null) {
			String whereClauseQuery = filterCriteriaPopulation(filters, CommonConstants.WHERECLAUSE);
			if (whereClauseQuery != null && !whereClauseQuery.isEmpty()) {
				if (sb.toString().contains(CommonConstants.WHERE))
					sb.append(CommonConstants._AND_);
				else
					sb.append(CommonConstants._WHERE_);
				sb.append(whereClauseQuery);
			}
		}
		sb.append(GROUP_BY_LABEL);
		if (detailedAnalysis != null
				&& Utils.checkCollectionHasKeyAndValue(detailedAnalysis, CommonConstants.GROUPBY)) {
			sb.append(", " + detailedAnalysis.get(CommonConstants.GROUPBY).toString());
		}

		logger.debug(CLASSNAME + " >> genderMixtureVisualisation() >> Query ");
		logger.debug(sb.toString());
		List<Map<String, Object>> queryResponse = jdbcTemplate.queryForList(sb.toString());
		logger.debug(CLASSNAME + " >> genderMixtureVisualisation() >> Query Response= "+queryResponse.size());
		
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
		} else if (chartType.equalsIgnoreCase(CommonConstants.MULTIPLE_COMPARISON_BARCHART)) {
			return echartService.convertToMultipleComparisonBarChart(queryResponse, crb);
		}
		return queryResponse;
	}

	@Override
	public Object offerDeclineVisualisation(ChartRequestModel crb) throws BaseException {
		logger.debug(CLASSNAME + " >> offerDeclineVisualisation() >> START");
		String chartType = crb.getChartType();
		Map<String, Object> filters = crb.getFilters();
		Map<String, Object> detailedAnalysis = detailedAnalysisRequested(crb);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT cdc.category as label");
		if (crb.isPercentageRequested())
			sb.append(COUNT_IN_PERCENTAGE);
		else
			sb.append(COUNT_IN_INTEGER);
		if (detailedAnalysis != null && Utils.checkCollectionHasKeyAndValue(detailedAnalysis, CommonConstants.QUERY)) {
			sb.append(", " + detailedAnalysis.get(CommonConstants.QUERY).toString());
		}
		sb.append(FROM_CANDIDATE);
		sb.append(" JOIN candidate_offer co ON c.candidate_offer_status=co.id");
		sb.append(" JOIN offer_status os ON co.status=os.id");
		sb.append(" JOIN offer_decline_category cdc ON co.decline_category=cdc.id");
		if (detailedAnalysis != null && Utils.checkCollectionHasKeyAndValue(detailedAnalysis, CommonConstants.JOIN)) {
			sb.append(detailedAnalysis.get(CommonConstants.JOIN).toString());
		}
		if (filters != null) {
			String joinQuery = filterCriteriaPopulation(filters, CommonConstants.JOIN);
			if (joinQuery != null && !joinQuery.isEmpty())
				sb.append(joinQuery);
		}
		if (filters != null) {
			String whereClauseQuery = filterCriteriaPopulation(filters, CommonConstants.WHERECLAUSE);
			if (whereClauseQuery != null && !whereClauseQuery.isEmpty()) {
				if (sb.toString().contains(CommonConstants.WHERE))
					sb.append(CommonConstants._AND_);
				else
					sb.append(CommonConstants._WHERE_);
				sb.append(whereClauseQuery);
			}
		}
		sb.append(GROUP_BY_LABEL);
		if (detailedAnalysis != null
				&& Utils.checkCollectionHasKeyAndValue(detailedAnalysis, CommonConstants.GROUPBY)) {
			sb.append(", " + detailedAnalysis.get(CommonConstants.GROUPBY).toString());
		}

		logger.debug(CLASSNAME + " >> offerDeclineVisualisation() >> Query ");
		logger.debug(sb.toString());
		List<Map<String, Object>> queryResponse = jdbcTemplate.queryForList(sb.toString());
		logger.debug(CLASSNAME + " >> offerDeclineVisualisation() >> Query Response= "+queryResponse.size());
		
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
		} else if (chartType.equalsIgnoreCase(CommonConstants.MULTIPLE_COMPARISON_BARCHART)) {
			return echartService.convertToMultipleComparisonBarChart(queryResponse, crb);
		}
		return queryResponse;
	}

	private String filterCriteriaPopulation(Map<String, Object> filters, String requestedString) {
		logger.debug(CLASSNAME + " >> filterCriteriaPopulation() >> Query ");
		ArrayList<String> joinClauses = new ArrayList<>();
		ArrayList<String> whereClauses = new ArrayList<>();
		if (Utils.checkCollectionHasKeyAndValueForFilters(filters, CommonConstants.FINANCIAL_YEAR)) {
			if (requestedString.equals(CommonConstants.JOIN))
				joinClauses.add(" JOIN financial_year fy ON c.financial_year=fy.id");
			else if (requestedString.equals(CommonConstants.WHERECLAUSE))
				whereClauses.add("fy.id='" + filters.get(CommonConstants.FINANCIAL_YEAR) + "'");
		}

		if (Utils.checkCollectionHasKeyAndValueForFilters(filters, CommonConstants.MARKET_OFFERING)) {
			if (requestedString.equals(CommonConstants.JOIN))
				joinClauses.add(" JOIN market_subbusinessline mbl ON c.sub_business_line=mbl.id");
			else if (requestedString.equals(CommonConstants.WHERECLAUSE))
				whereClauses
						.add("mbl.market_offering='" + filters.get(CommonConstants.MARKET_OFFERING) + "'");
		}

		if (requestedString.equals(CommonConstants.JOIN))
			return String.join(" ", joinClauses);

		if (requestedString.equals(CommonConstants.WHERECLAUSE))
			return String.join(CommonConstants._AND_, whereClauses);

		return null;
	}

	private HashMap<String, Object> detailedAnalysisRequested(ChartRequestModel crm) {
		logger.debug(CLASSNAME + " >> detailedAnalysisRequested() >> START ");
		ArrayList<String> joinClauses = new ArrayList<>();
		ArrayList<String> queryClauses = new ArrayList<>();
		ArrayList<String> groupByClauses = new ArrayList<>();

		HashMap<String, Object> endResponse = new HashMap<>();

		if (crm.getDetailedAnalaysis() != null && !crm.getDetailedAnalaysis().isEmpty()) {
			for (Map<String, Object> details : crm.getDetailedAnalaysis()) {
				String detailsBy = details.get("by").toString();
				if (detailsBy.equals(CommonConstants.FINANCIAL_YEAR)) {
					joinClauses.add(" JOIN financial_year fy ON c.financial_year=fy.id");
					queryClauses.add(" fy.financial_year as " + detailsBy);
					groupByClauses.add(detailsBy);
				} else if (detailsBy.equals(CommonConstants.MARKET_OFFERING)) {
					joinClauses.add(
							" JOIN market_subbusinessline mbl ON c.sub_business_line=mbl.id JOIN market_offering mo ON mbl.market_offering=mo.id");
					queryClauses.add(" mo.market as " + detailsBy);
					groupByClauses.add(detailsBy);
				}
			}

			endResponse.put(CommonConstants.QUERY, String.join(", ", queryClauses));
			endResponse.put(CommonConstants.JOIN, String.join(" ", joinClauses));
			endResponse.put(CommonConstants.GROUPBY, String.join(", ", groupByClauses));

			return endResponse;
		}
		return null;
	}

}
