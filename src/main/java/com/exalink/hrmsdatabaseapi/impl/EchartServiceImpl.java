package com.exalink.hrmsdatabaseapi.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.Utils;
import com.exalink.hrmsdatabaseapi.model.ChartRequestModel;
import com.exalink.hrmsdatabaseapi.service.IEchartService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ankitkverma
 *
 */
@Service
public class EchartServiceImpl implements IEchartService{
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Object convertToBarChart(List<Map<String, Object>> chartData, ChartRequestModel crb) {
		/*
		 Bar Chart Sample
		 https://echarts.apache.org/examples/en/editor.html?c=bar-simple
		 */
		List<String> xAxisData = new ArrayList<>();
		List<Integer> yAxisData = new ArrayList<>();
		chartData.forEach(mapObject -> {
			xAxisData.add(mapObject.get(LABEL).toString());
			if(mapObject.get(VALUE) instanceof BigDecimal)
				yAxisData.add(((BigDecimal)mapObject.get(VALUE)).intValue());
			else
				yAxisData.add((int)mapObject.get(VALUE));
		});
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(barChatrt, HashMap.class);
			((Map<String,Object>)chartStaticJsonCollectionHolder.get("xAxis")).put(DATA, xAxisData);
			((Map<String,Object>)((List<Object>)chartStaticJsonCollectionHolder.get(SERIES)).get(0)).put(DATA, yAxisData);
			((Map<String,Object>)chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA, Utils.labelListExtractor(chartData));
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, crb);
			return chartStaticJsonCollectionHolder;
		}catch (Exception e) {
			
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToPieChart(List<Map<String, Object>> chartData, ChartRequestModel crb) {
		/*
		 Pie Chart Sample
		 https://echarts.apache.org/examples/en/editor.html?c=pie-custom
		 */
		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(pieChart, HashMap.class);
			((Map<String,Object>)((List<Object>)chartStaticJsonCollectionHolder.get(SERIES)).get(0)).put(DATA, Utils.nameValuePairGenerator(chartData));
			((Map<String,Object>)chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA, Utils.labelListExtractor(chartData));
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, crb);
			return chartStaticJsonCollectionHolder;
		}catch (Exception e) {
			
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToDoughnutChart(List<Map<String, Object>> chartData, ChartRequestModel crb) {
		/*
		 Doughnut Chart Sample
		 https://echarts.apache.org/examples/en/editor.html?c=pie-doughnut
		 */
		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(doughnutChart, HashMap.class);
			((Map<String,Object>)((List<Object>)chartStaticJsonCollectionHolder.get(SERIES)).get(0)).put(DATA, Utils.nameValuePairGenerator(chartData));
			((Map<String,Object>)chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA, Utils.labelListExtractor(chartData));
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, crb);
			return chartStaticJsonCollectionHolder;
		}catch (Exception e) {
			
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToSimplePieChart(List<Map<String, Object>> chartData, ChartRequestModel crb) {
		/*
		 Doughnut Chart Sample
		 https://echarts.apache.org/examples/en/editor.html?c=pie-doughnut
		 */
		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(simplePieChart, HashMap.class);
			((Map<String,Object>)((List<Object>)chartStaticJsonCollectionHolder.get(SERIES)).get(0)).put(DATA, Utils.nameValuePairGenerator(chartData));
			((Map<String,Object>)chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA, Utils.labelListExtractor(chartData));
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, crb);
			return chartStaticJsonCollectionHolder;
		}catch (Exception e) {
			
		}
		return chartData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToHorizontalBarChart(List<Map<String, Object>> chartData, ChartRequestModel crb) {
		/*
		 * Horizontal Bar Chart
		 * https://echarts.apache.org/examples/en/editor.html?c=dataset-encode0&theme=light
		 */
		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, Object> chartStaticJsonCollectionHolder = mapper.readValue(horizontalBarChart, HashMap.class);
			((Map<String,Object>)chartStaticJsonCollectionHolder.get("dataset")).put("source", Utils.labelValueArrayOfArrayGenerator(chartData));
			((Map<String,Object>)chartStaticJsonCollectionHolder.get(LEGEND)).put(DATA, Utils.labelListExtractor(chartData));
			populateTitleAndSubTitle(chartStaticJsonCollectionHolder, crb);
			return chartStaticJsonCollectionHolder;
		}catch (Exception e) {
			
		}
		return chartData;
	}
	
	@SuppressWarnings("unchecked")
	private void populateTitleAndSubTitle(HashMap<String, Object> chartStaticJsonCollectionHolder, ChartRequestModel crb){
		if(crb.getTitle()!=null) {
			((Map<String,Object>)chartStaticJsonCollectionHolder.get(TITLE)).put(TEXT, crb.getTitle());
		}
		if(crb.getSubTitle()!=null) {
			((Map<String,Object>)chartStaticJsonCollectionHolder.get(TITLE)).put(SUBTEXT, crb.getSubTitle());
		}
	}
	
}
