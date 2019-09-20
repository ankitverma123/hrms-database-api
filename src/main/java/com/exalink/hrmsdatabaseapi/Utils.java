package com.exalink.hrmsdatabaseapi;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author ankitkverma
 *
 */
public class Utils {
	
	private static final String LABEL = "label";
	private static final String VALUE = "value";
	private static final String NAME = "name";
	
	private Utils() {
	    throw new IllegalStateException("Utility class");
	  }

	
	public static List<Map<String, Object>> nameValuePairGenerator(List<Map<String, Object>> chartData) {
		List<Map<String, Object>> nameValueCollection = new ArrayList<>();
		chartData.forEach(mapObject -> {
			Map<String, Object> pieData = new HashMap<>();
			pieData.put(NAME, mapObject.get(LABEL).toString());
			if(mapObject.get(VALUE) instanceof BigDecimal)
				pieData.put(VALUE, ((BigDecimal)mapObject.get(VALUE)).intValue());
			else if(mapObject.get(VALUE) instanceof BigInteger)
				pieData.put(VALUE, ((BigInteger)mapObject.get(VALUE)).intValue());
			else if(mapObject.get(VALUE) instanceof Long)
				pieData.put(VALUE, ((Long)mapObject.get(VALUE)).intValue());
			else
				pieData.put(VALUE, (int)mapObject.get(VALUE));
			nameValueCollection.add(pieData);
		});
		return nameValueCollection;
	}
	
	public static List<String> labelListExtractor(List<Map<String, Object>> chartData) {
		List<String> xAxisData = new ArrayList<>();
		chartData.forEach(mapObject -> {
			xAxisData.add(mapObject.get(LABEL).toString());
		});
		return xAxisData;
	}
	
	public static List<Integer> valueListExtractor(List<Map<String, Object>> chartData) {
		List<Integer> yAxisData = new ArrayList<>();
		chartData.forEach(mapObject -> {
			if(mapObject.get(VALUE) instanceof BigDecimal)
				yAxisData.add(((BigDecimal)mapObject.get(VALUE)).intValue());
			else if(mapObject.get(VALUE) instanceof BigInteger)
				yAxisData.add(((BigInteger)mapObject.get(VALUE)).intValue());
			else if(mapObject.get(VALUE) instanceof Long)
				yAxisData.add(((Long)mapObject.get(VALUE)).intValue());
			else
				yAxisData.add((int)mapObject.get(VALUE));
		});
		return yAxisData;
	}
	
	public static List<ArrayList<Object>> labelValueArrayOfArrayGenerator(List<Map<String, Object>> chartData){
		List<ArrayList<Object> > collector = new ArrayList<>();
		for(int i=0;i<chartData.size();i++) {
			Map<String, Object> mapObject = chartData.get(i);
			if(mapObject.get(VALUE) instanceof BigDecimal) {
				collector.add(new ArrayList<Object>(Arrays.asList(((BigDecimal)mapObject.get(VALUE)).intValue(),  mapObject.get(LABEL).toString())));
			}
			else if(mapObject.get(VALUE) instanceof BigInteger) {
				collector.add(new ArrayList<Object>(Arrays.asList(((BigInteger)mapObject.get(VALUE)).intValue(),  mapObject.get(LABEL).toString())));
			}
			else if(mapObject.get(VALUE) instanceof Long) {
				collector.add(new ArrayList<Object>(Arrays.asList(((Long)mapObject.get(VALUE)).intValue(),  mapObject.get(LABEL).toString())));
			}
			else {
				collector.add(new ArrayList<Object>(Arrays.asList((int)mapObject.get(VALUE),  mapObject.get(LABEL).toString())));
			}
		}
		return collector;
	}
	
	public static boolean checkCollectionHasKeyAndValue(Map<String, Object> map, String key){
		boolean result=false;
		if(map.containsKey(key) && map.get(key)!=null && !map.get(key).toString().isEmpty())
			result= true;
		return result;
	}
	
	public static boolean checkCollectionHasKeyAndValueForFilters(Map<String, Object> map, String key){
		boolean result=false;
		if(map.containsKey(key) && map.get(key)!=null && !map.get(key).toString().isEmpty() && !map.get(key).toString().equals("-1"))
			result= true;
		return result;
	}
	
	public static Set<String> keyExtractorFromCollection(List<Map<String, Object>> chartData, String key) {
		Set<String> setCollector = new HashSet<>();
		chartData.forEach(mapObject -> {
			setCollector.add(mapObject.get(key).toString());
		});
		return setCollector;
	}
	
}
