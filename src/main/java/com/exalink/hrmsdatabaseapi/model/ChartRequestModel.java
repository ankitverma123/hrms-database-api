package com.exalink.hrmsdatabaseapi.model;

import java.util.Map;

import lombok.Data;

/**
 * @author ankitkverma
 *
 */
@Data
public class ChartRequestModel {
	private String chartType;
	private boolean percentageRequested;
	private String title;
	private String subTitle;
	private String series;
	private Map<String, Object> filters;
}
