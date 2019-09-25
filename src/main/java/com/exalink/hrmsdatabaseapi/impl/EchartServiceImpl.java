package com.exalink.hrmsdatabaseapi.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.CommonConstants;
import com.exalink.hrmsdatabaseapi.Utils;
import com.exalink.hrmsdatabaseapi.model.ChartRequestModel;
import com.exalink.hrmsdatabaseapi.service.IEchartService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ankitkverma
 *
 */
@Service
public class EchartServiceImpl implements IEchartService {

	private static final Logger logger = LogManager.getLogger(EchartServiceImpl.class);
	private static final String CLASSNAME = EchartServiceImpl.class.getName();
	
	private static final String LABEL = "label";
	private static final String VALUE = "value";
	private static final String SERIES = "series";
	private static final String DATA = "data";

	private static final String TITLE = "title";
	private static final String TEXT = "text";
	private static final String SUBTEXT = "subtext";

	private static final String LEGEND = "legend";

	@Value("${charts.barchart}")
	private String barChatrt;

	@Value("${charts.piechart}")
	private String pieChart;

	@Value("${charts.doughnutchart}")
	private String doughnutChart;

	@Value("${charts.simplepiechart}")
	private String simplePieChart;

	@Value("${charts.horizontalbarchart}")
	private String horizontalBarChart;

	@Value("${charts.multibarchart}")
	private String multibarchart;

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToBarChart(List<Map<String, Object>> chartData, ChartRequestModel crb) {
		logger.debug(CLASSNAME + " >> convertToBarChart() >> START");
		/*
		 * Bar Chart Sample
		 * https://echarts.apache.org/examples/en/editor.html?c=bar-simple
		 */
		List<String> xAxisData = new ArrayList<>();
		List<Integer> yAxisData = new ArrayList<>();
		chartData.forEach(mapObject -> {
			xAxisData.add(mapObject.get(LABEL).toString());
			if (mapObject.get(VALUE) instanceof BigDecimal)
				yAxisData.add(((BigDecimal) mapObject.get(VALUE)).intValue());
			if (mapObject.get(VALUE) instanceof Long)
				yAxisData.add(Integer.parseInt(mapObject.get(VALUE).toString()));
			else
				yAxisData.add((int) mapObject.get(VALUE));
		});

		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(barChatrt, HashMap.class);
			((Map<String, Object>) (((List<Object>) chartStaticJsonCollectionHolder.get("xAxis")).get(0))).put(DATA,
					xAxisData);
			((Map<String, Object>) ((List<Object>) chartStaticJsonCollectionHolder.get(SERIES)).get(0)).put(DATA,
					yAxisData);
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA,
					Utils.labelListExtractor(chartData));
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, crb);
			populateSeriesName(chartStaticJsonCollectionHolder, crb);
			return chartStaticJsonCollectionHolder;
		} catch (Exception e) {
			logger.debug(CLASSNAME + " >> convertToBarChart() >> Exception= "+e.getMessage());
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToPieChart(List<Map<String, Object>> chartData, ChartRequestModel crb) {
		logger.debug(CLASSNAME + " >> convertToPieChart() >> START");
		/*
		 * Pie Chart Sample
		 * https://echarts.apache.org/examples/en/editor.html?c=pie-custom
		 */
		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(pieChart, HashMap.class);
			((Map<String, Object>) ((List<Object>) chartStaticJsonCollectionHolder.get(SERIES)).get(0)).put(DATA,
					Utils.nameValuePairGenerator(chartData));
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA,
					Utils.labelListExtractor(chartData));
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, crb);
			populateSeriesName(chartStaticJsonCollectionHolder, crb);
			return chartStaticJsonCollectionHolder;
		} catch (Exception e) {
			logger.debug(CLASSNAME + " >> convertToPieChart() >> Exception= "+e.getMessage());
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToDoughnutChart(List<Map<String, Object>> chartData, ChartRequestModel crb) {
		logger.debug(CLASSNAME + " >> convertToDoughnutChart() >> START");
		/*
		 * Doughnut Chart Sample
		 * https://echarts.apache.org/examples/en/editor.html?c=pie-doughnut
		 */
		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(doughnutChart, HashMap.class);
			((Map<String, Object>) ((List<Object>) chartStaticJsonCollectionHolder.get(SERIES)).get(0)).put(DATA,
					Utils.nameValuePairGenerator(chartData));
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA,
					Utils.labelListExtractor(chartData));
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, crb);
			populateSeriesName(chartStaticJsonCollectionHolder, crb);
			return chartStaticJsonCollectionHolder;
		} catch (Exception e) {
			logger.debug(CLASSNAME + " >> convertToDoughnutChart() >> Exception= "+e.getMessage());
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToSimplePieChart(List<Map<String, Object>> chartData, ChartRequestModel crb) {
		logger.debug(CLASSNAME + " >> convertToSimplePieChart() >> START");
		/*
		 * Doughnut Chart Sample
		 * https://echarts.apache.org/examples/en/editor.html?c=pie-doughnut
		 */
		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(simplePieChart, HashMap.class);
			((Map<String, Object>) ((List<Object>) chartStaticJsonCollectionHolder.get(SERIES)).get(0)).put(DATA,
					Utils.nameValuePairGenerator(chartData));
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA,
					Utils.labelListExtractor(chartData));
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, crb);
			populateSeriesName(chartStaticJsonCollectionHolder, crb);
			return chartStaticJsonCollectionHolder;
		} catch (Exception e) {
			logger.debug(CLASSNAME + " >> convertToSimplePieChart() >> Exception= "+e.getMessage());
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToHorizontalBarChart(List<Map<String, Object>> chartData, ChartRequestModel crb) {
		logger.debug(CLASSNAME + " >> convertToHorizontalBarChart() >> START");
		/*
		 * Horizontal Bar Chart
		 * https://echarts.apache.org/examples/en/editor.html?c=dataset-encode0&theme=
		 * light
		 */
		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(horizontalBarChart,
					HashMap.class);
			((Map<String, Object>) chartStaticJsonCollectionHolder.get("dataset")).put("source",
					Utils.labelValueArrayOfArrayGenerator(chartData));
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA,
					Utils.labelListExtractor(chartData));
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, crb);
			return chartStaticJsonCollectionHolder;
		} catch (Exception e) {
			logger.debug(CLASSNAME + " >> convertToHorizontalBarChart() >> Exception= "+e.getMessage());
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	private void populateTitleAndSubTitle(HashMap<String, Object> chartStaticJsonCollectionHolder,
			ChartRequestModel crb) {
		if (crb.getTitle() != null) {
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(TITLE)).put(TEXT, crb.getTitle());
		}
		if (crb.getSubTitle() != null) {
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(TITLE)).put(SUBTEXT, crb.getSubTitle());
		}
	}

	@SuppressWarnings("unchecked")
	private void populateSeriesName(HashMap<String, Object> chartStaticJsonCollectionHolder, ChartRequestModel crb) {
		if (chartStaticJsonCollectionHolder.containsKey(SERIES) && crb.getSeries() != null) {
			((Map<String, Object>) ((List<Object>) chartStaticJsonCollectionHolder.get(SERIES)).get(0))
					.put(CommonConstants.NAME, crb.getSeries());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToMultipleComparisonBarChart(List<Map<String, Object>> chartData, ChartRequestModel crb) {
		logger.debug(CLASSNAME + " >> convertToMultipleComparisonBarChart() >> START");
		/*
		 * Multiple Vertical Comparison Bar Chart
		 * https://echarts.apache.org/examples/en/editor.html?c=bar-label-rotation
		 * light
		 */
		String comparisonByColumn = crb.getDetailedAnalaysis().get(0).get("by").toString();

		List<String> legends = Utils.keyExtractorFromCollection(chartData, LABEL).stream().collect(Collectors.toList());
		List<String> detailedComparisonCategoryBy = Utils.keyExtractorFromCollection(chartData, comparisonByColumn.toLowerCase())
				.stream().collect(Collectors.toList());

		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(multibarchart, HashMap.class);
			(((List<Map<String, Object>>) chartStaticJsonCollectionHolder.get("xAxis")).get(0)).put(DATA,
					detailedComparisonCategoryBy);
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA, legends);
			HashMap<String, Object> seriesObj = ((List<HashMap<String, Object>>) chartStaticJsonCollectionHolder.get(SERIES))
					.get(0);
			List<Map<String, Object>> seriesObjReadData = new ArrayList<>();
			int totalLegends = legends.size();
			for (int i = 0; i < totalLegends; i++) {
				HashMap<String, Object> newObj = (HashMap<String, Object>) seriesObj.clone();
				String legendName = legends.get(i);
				newObj.put(CommonConstants.NAME, legendName);
				List<Integer> legenedData = chartData.stream().filter(obj -> obj.get(LABEL).toString().equals(legendName))
						.map(obj -> Integer.valueOf(obj.get(VALUE).toString())).collect(Collectors.toList());
				
				newObj.put(DATA, legenedData);
				seriesObjReadData.add(newObj);
			}
			chartStaticJsonCollectionHolder.put(SERIES, seriesObjReadData);
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, crb);
			return chartStaticJsonCollectionHolder;
		} catch (Exception e) {
			logger.debug(CLASSNAME + " >> convertToMultipleComparisonBarChart() >> Exception= "+e.getMessage());
		}
		return chartData;
	}
}
