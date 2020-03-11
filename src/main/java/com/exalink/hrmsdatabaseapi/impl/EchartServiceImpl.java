package com.exalink.hrmsdatabaseapi.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.model.ChartRequestModel;
import com.exalink.hrmsdatabaseapi.service.IEchartService;
import com.exalink.hrmsfabric.common.CommonConstants;
import com.exalink.hrmsfabric.common.Utils;
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
	
	@Value("${charts.comparisonHorizontalChart}")
	private String comparisonHorizontalChart;
	
	
	private Map<String, String> formatter() {
		Map<String, String> formatter = new HashMap<String, String>();
		formatter.put("formatter", "{b} : {c} ({d}%)");
		return formatter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToBarChart(List<Map<String, Object>> chartData, String legendKey, String xAxisKey, String title, String subTitle) {
		/*
		 * Bar Chart Sample
		 * https://echarts.apache.org/examples/en/editor.html?c=bar-simple
		 */
		List<String> xAxisData = new ArrayList<>();
		List<Integer> yAxisData = new ArrayList<>();
		chartData.forEach(mapObject -> {
			if(mapObject.get(xAxisKey)!=null && mapObject.get(VALUE)!=null) {
				xAxisData.add(mapObject.get(xAxisKey).toString());
				if (mapObject.get(VALUE) instanceof BigDecimal)
					yAxisData.add(((BigDecimal) mapObject.get(VALUE)).intValue());
				if (mapObject.get(VALUE) instanceof Long)
					yAxisData.add(Integer.parseInt(mapObject.get(VALUE).toString()));
				else
					yAxisData.add((int) mapObject.get(VALUE));
			}
		});
		
		List<String> legends = Utils.keyExtractorFromCollection(chartData, legendKey).stream().collect(Collectors.toList());

		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(barChatrt, HashMap.class);
			((Map<String, Object>) (((List<Object>) chartStaticJsonCollectionHolder.get("xAxis")).get(0))).put(DATA,
					xAxisData);
			((Map<String, Object>) ((List<Object>) chartStaticJsonCollectionHolder.get(SERIES)).get(0)).put(DATA,
					yAxisData);
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA, legends);
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, title, subTitle);
			return chartStaticJsonCollectionHolder;
		} catch (Exception e) {
			logger.error(e);
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToPieChart(List<Map<String, Object>> chartData, ChartRequestModel crb) {
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
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, null, null);
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
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, null, null);
			populateSeriesName(chartStaticJsonCollectionHolder, crb);
			return chartStaticJsonCollectionHolder;
		} catch (Exception e) {
			logger.error(e);
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToSimplePieChart(List<Map<String, Object>> chartData, String legendKey, String xAxisKey, String title, String subTitle) {
		logger.debug(CLASSNAME + " >> convertToSimplePieChart() >> START");
		/*
		 * Doughnut Chart Sample
		 * https://echarts.apache.org/examples/en/editor.html?c=pie-doughnut
		 */
		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(simplePieChart, HashMap.class);
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, title, subTitle);
			
			List<String> legends = Utils.keyExtractorFromCollection(chartData, legendKey).stream().collect(Collectors.toList());
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA,legends);
			
			List<Map<String, Object>> chartDataFormatted = new ArrayList<>();
			for(Map<String, Object> obj : chartData) {
				Map<String, Object> mapObj = new HashMap<>();
				if(obj.get(xAxisKey)!=null && obj.get(VALUE)!=null) {
					mapObj.put(CommonConstants.NAME, (String)obj.get(xAxisKey));
					mapObj.put(VALUE, obj.get(VALUE));
					mapObj.put(LABEL, formatter());
					chartDataFormatted.add(mapObj);
				}
			}
			((Map<String, Object>) ((List<Object>) chartStaticJsonCollectionHolder.get(SERIES)).get(0)).put(DATA,chartDataFormatted);
			
			
			((Map<String, Object>) ((List<Object>) chartStaticJsonCollectionHolder.get(SERIES)).get(0)).put(CommonConstants.NAME, title);
			return chartStaticJsonCollectionHolder;
		} catch (Exception e) {
			logger.debug(CLASSNAME + " >> convertToSimplePieChart() >> Exception= "+e.getMessage());
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToHorizontalBarChart(List<Map<String, Object>> chartData, String legendKey, String xAxisKey, String title, String subTitle) {
		logger.debug(CLASSNAME + " >> convertToHorizontalBarChart() >> START");
		/*
		 * Horizontal Bar Chart
		 * https://echarts.apache.org/examples/en/editor.html?c=dataset-encode0&theme=
		 * light
		 */
		List<String> legends = Utils.keyExtractorFromCollection(chartData, legendKey).stream().collect(Collectors.toList());
		
		
		List<ArrayList<Object> > collector = new ArrayList<>();
		for(int i=0;i<chartData.size();i++) {
			Map<String, Object> mapObject = chartData.get(i);
			if(mapObject.get(xAxisKey)!=null && mapObject.get(VALUE)!=null) {
				collector.add(new ArrayList<Object>(Arrays.asList(mapObject.get(VALUE),  mapObject.get(xAxisKey).toString())));
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(horizontalBarChart,
					HashMap.class);
			((Map<String, Object>) chartStaticJsonCollectionHolder.get("dataset")).put("source", collector);
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA,legends);
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, title, subTitle);
			return chartStaticJsonCollectionHolder;
		} catch (Exception e) {
			logger.error(e);
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	private void populateTitleAndSubTitle(HashMap<String, Object> chartStaticJsonCollectionHolder,
			String title, String subTitle) {
		if(title!=null) {
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(TITLE)).put(TEXT, title);
		}
		if(subTitle!=null) {
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(TITLE)).put(SUBTEXT, subTitle);
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
	public Object convertToMultipleComparisonBarChart(List<Map<String, Object>> chartData, String legendKey, String xAxisKey, String title, String subTitle) {
		logger.debug(CLASSNAME + " >> convertToMultipleComparisonBarChart() >> START");
		/*
		 * Multiple Vertical Comparison Bar Chart
		 * https://echarts.apache.org/examples/en/editor.html?c=bar-label-rotation
		 * light
		 */
		String comparisonByColumn = xAxisKey;

		List<String> legends = Utils.keyExtractorFromCollection(chartData, legendKey).stream().collect(Collectors.toList());
		List<String> xAxises = Utils.keyExtractorFromCollection(chartData, comparisonByColumn)
				.stream().collect(Collectors.toList());

		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(multibarchart, HashMap.class);
			(((List<Map<String, Object>>) chartStaticJsonCollectionHolder.get("xAxis")).get(0)).put(DATA,
					xAxises);
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA, legends);
			HashMap<String, Object> seriesObj = ((List<HashMap<String, Object>>) chartStaticJsonCollectionHolder.get(SERIES))
					.get(0);
			List<Map<String, Object>> seriesObjReadData = new ArrayList<>();
			
			int totalLegends = legends.size();
			int totalXAxises = xAxises.size();
			
			for(int i=0;i<totalLegends;i++) {
				HashMap<String, Object> newObj = (HashMap<String, Object>) seriesObj.clone();
				String legendName = legends.get(i);
				System.out.println("legendName= "+legendName);
				newObj.put(CommonConstants.NAME, legendName);
				List<Object> valueCollector = new ArrayList<>();
				for(int j=0;j<totalXAxises;j++) {
					String xAxis = xAxises.get(j);
					System.out.println("xAxis= "+xAxis);
					List<Object> valueList = chartData.stream().filter(obj -> obj.get(legendKey)!=null && obj.get(legendKey).toString().equals(legendName) && obj.get(xAxisKey)!=null && obj.get(xAxisKey).toString().equals(xAxis))
					.map(obj -> obj.get(VALUE))
					.collect(Collectors.toList());
					System.out.println("valueList= "+valueList);
					valueCollector.add(valueList.isEmpty() ? 0 : valueList.get(0));
				}
				newObj.put(DATA, valueCollector);
				seriesObjReadData.add(newObj);
			}
			chartStaticJsonCollectionHolder.put(SERIES, seriesObjReadData);
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, title, subTitle);
			return chartStaticJsonCollectionHolder;
		} catch (Exception e) {
			logger.debug(CLASSNAME + " >> convertToMultipleComparisonBarChart() >> Exception= "+e.getMessage());
		}
		return chartData;
	}

	@Override
	public Object convertToHorizontanComparisonBarChart(List<Map<String, Object>> chartData, String legendKey,
			String xAxisKey, String title, String subTitle) {
		List<String> legends = Utils.keyExtractorFromCollection(chartData, legendKey).stream().collect(Collectors.toList());
		List<String> xAxises = Utils.keyExtractorFromCollection(chartData, xAxisKey).stream().collect(Collectors.toList());

		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(comparisonHorizontalChart, HashMap.class);
			
			((Map<String, Object>) chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA, legends);
			((Map<String, Object>) chartStaticJsonCollectionHolder.get("yAxis")).put(DATA, xAxises);
			
			HashMap<String, Object> seriesObj = ((List<HashMap<String, Object>>) chartStaticJsonCollectionHolder.get(SERIES)).get(0);
			List<Map<String, Object>> seriesObjReadData = new ArrayList<>();
			
			int totalLegends = legends.size();
			int totalXAxises = xAxises.size();
			
			for(int i=0;i<totalLegends;i++) {
				HashMap<String, Object> newObj = (HashMap<String, Object>) seriesObj.clone();
				String legendName = legends.get(i);
				newObj.put(CommonConstants.NAME, legendName);
				List<Object> valueCollector = new ArrayList<>();
				for(int j=0;j<totalXAxises;j++) {
					String xAxis = xAxises.get(j);
					List<Object> valueList = chartData.stream().filter(obj -> obj.get(legendKey)!=null && obj.get(legendKey).toString().equals(legendName) && obj.get(xAxisKey)!=null && obj.get(xAxisKey).toString().equals(xAxis))
					.map(obj -> obj.get(VALUE))
					.collect(Collectors.toList());
					valueCollector.add(valueList.isEmpty() ? 0 : valueList.get(0));
				}
				newObj.put(DATA, valueCollector);
				seriesObjReadData.add(newObj);
			}
			chartStaticJsonCollectionHolder.put(SERIES, seriesObjReadData);
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, title, subTitle);
			return chartStaticJsonCollectionHolder;
		} catch (Exception e) {
			logger.error(e);
		}
		return chartData;
	}
}
