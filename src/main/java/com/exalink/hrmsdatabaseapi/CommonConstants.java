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
	
	public static final String BAR_CHART= "bar";
	public static final String HORIZONTAL_BAR_CHART= "horizontalbar";
	public static final String PIE_CHART= "pie";
	public static final String SIMPLE_PIE_CHART= "simplepie";
	public static final String DOUGHNUT_CHART= "doughnut";
	
	public static final String WHERE= "WHERE";
	public static final String _WHERE_= " WHERE ";
	public static final String AND= "AND";
	public static final String _AND_= " AND ";
	
	public static final String SORT_DIRECTION_ASC= "asc";
	public static final String SORT_DIRECTION_DESC= "desc";
	
	public static final String RESULTS= "results";
	public static final String TOTAL_COUNT= "totalCount";
	
	public static final String FINANCIAL_YEAR= "FINANCIALYEAR";
	public static final String CANDIDATE_SOURCE= "CANDIDATESOURCE";
	public static final String ONBOARD_STATUS= "ONBOARDSTATUS";
	public static final String OFFER_DECLINE_CATEGORIES= "OFFERDECLINECATEGORIES";
}
