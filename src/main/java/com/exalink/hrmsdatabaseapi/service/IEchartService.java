package com.exalink.hrmsdatabaseapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.model.ChartRequestModel;

/**
 * @author ankitkverma
 *
 */
@Service
public interface IEchartService {
	Object convertToBarChart(List<Map<String, Object>> chartData, String legendKey, String xAxisKey, String title, String subTitle);
	Object convertToPieChart(List<Map<String, Object>> chartData, ChartRequestModel crb);
	Object convertToSimplePieChart(List<Map<String, Object>> chartData, String legendKey, String xAxisKey, String title, String subTitle);
	Object convertToDoughnutChart(List<Map<String, Object>> chartData, ChartRequestModel crb);
	Object convertToHorizontalBarChart(List<Map<String, Object>> chartData, String legendKey, String xAxisKey, String title, String subTitle);
	Object convertToMultipleComparisonBarChart(List<Map<String, Object>> chartData, String legendKey, String xAxisKey, String title, String subTitle);
	Object convertToHorizontanComparisonBarChart(List<Map<String, Object>> chartData, String legendKey, String xAxisKey, String title, String subTitle);
}
