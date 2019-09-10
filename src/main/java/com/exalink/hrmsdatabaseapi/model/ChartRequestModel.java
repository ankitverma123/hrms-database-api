package com.exalink.hrmsdatabaseapi.model;

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
}
