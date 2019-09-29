package com.exalink.hrmsdatabaseapi;

/**
 * @author ankitkverma
 *
 */
public final class CommonConstants {
	
	private CommonConstants() {
	    throw new IllegalStateException("Utility class");
	  }

	public static final String CHART_VIEW_OVERALL= "Overall";
	public static final String CHART_VIEW_PERCENTAGE= "Percentage";
	public static final String CHART_VIEW_BOTH= "BothOverallPercentage";
	
	public static final String ACCEPTED= "Accepted";
	public static final String DECLINED= "Declined";
	public static final String STATUS= "status";
	public static final String CATEGORY= "category";
	public static final String COMMENT= "comment";
	public static final String NAME= "name";
	
	public static final String MARKET_OFFERING= "marketOffering";
	public static final String SUB_BUSINESS_LINE= "subBusinessLine";
	
	public static final String SUCCESS= "success";
	public static final String FAILURE= "failure";
	
	public static final String FILTER_BY_FINANCIAL_YEAR= "financialYearFilterId";
	public static final String FILTER_BY_MARKET_OFFERING= "marketOfferingFilterId";
	
	public static final String JOIN= "join";
	public static final String WHERECLAUSE= "where";
	public static final String QUERY= "query";
	public static final String GROUPBY= "groupBy";
	
	public static final String BAR_CHART= "bar";
	public static final String HORIZONTAL_BAR_CHART= "horizontalbar";
	public static final String PIE_CHART= "pie";
	public static final String SIMPLE_PIE_CHART= "simplepie";
	public static final String DOUGHNUT_CHART= "doughnut";
	public static final String MULTIPLE_COMPARISON_BARCHART= "multipleComparisonBarChart";
	
	public static final String WHERE= "WHERE";
	public static final String _WHERE_= " WHERE ";
	public static final String AND= "AND";
	public static final String _AND_= " AND ";
	
	public static final String SORT_DIRECTION_ASC= "asc";
	public static final String SORT_DIRECTION_DESC= "desc";
	
	public static final String RESULTS= "results";
	public static final String TOTAL_COUNT= "totalCount";
	
	public static final String ID= "id";
	public static final String LABEL= "label";
	
	public static final String FINANCIAL_YEAR= "financialYear";
	public static final String CANDIDATE_SOURCE= "candidateSources";
	public static final String ONBOARD_STATUS= "onboardStatus";
	public static final String OFFER_DECLINE_CATEGORIES= "offerDeclineCategories";
	
	public static final String CANDIDATE_REPORT_TEMPLATE= "Candidate Template";
	public static final String COMPREHENSIVE_REPORT_TEMPLATE= "Comprehensive Report";
	
	public static final String CSV_CANDIDATE_SOURCE_KEY= "candidateSource";
	public static final String CSV_ONBOARD_STATUS_KEY= "onboardStatus";
	public static final String CSV_FINANCIAL_YEAR_KEY= "financialYear";
	public static final String CSV_MARKET_OFFERING_KEY= "marketOffering";
	public static final String CSV_MARKET_SUB_BUSINESS_LINE_KEY= "marketSubBusinessLine";
	public static final String CSV_COMPETENCY_KEY= "competency";
	public static final String CSV_SUB_COMPETENCY_KEY= "subCompetency";
	
}
