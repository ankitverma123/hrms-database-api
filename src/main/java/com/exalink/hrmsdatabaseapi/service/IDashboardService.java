package com.exalink.hrmsdatabaseapi.service;

import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.model.ChartRequestModel;
import com.exalink.hrmsfabric.common.BaseException;

/**
 * @author ankitkverma
 *
 */
@Service
public interface IDashboardService {
	Object topSourcesVisualisation(ChartRequestModel crb) throws BaseException;
	Object recruitmentStatusVisualisation(ChartRequestModel crb) throws BaseException;
	Object genderMixtureVisualisation(ChartRequestModel crb) throws BaseException;
	Object offerDeclineVisualisation(ChartRequestModel crb) throws BaseException;
}
