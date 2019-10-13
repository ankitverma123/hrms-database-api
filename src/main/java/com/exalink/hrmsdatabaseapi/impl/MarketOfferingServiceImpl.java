package com.exalink.hrmsdatabaseapi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.entity.market.MarketOffering;
import com.exalink.hrmsdatabaseapi.entity.market.SubBusinessLine;
import com.exalink.hrmsdatabaseapi.repository.IMarketOfferingBusinessLineRepository;
import com.exalink.hrmsdatabaseapi.repository.IMarketOfferingRepository;
import com.exalink.hrmsdatabaseapi.service.IMarketOfferingService;
import com.exalink.hrmsfabric.common.CommonConstants;

/**
 * @author ankitkverma
 *
 */
@Service
public class MarketOfferingServiceImpl implements IMarketOfferingService{

	@Autowired
	private IMarketOfferingBusinessLineRepository subBusinessLineRepo;
	
	@Autowired
	private IMarketOfferingRepository marketOfferingRepo;
	
	@Override
	public List<MarketOffering> listAllMarketOffering() {
		return marketOfferingRepo.findAll();
	}

	@Override
	public List<SubBusinessLine> listAllSubBusinessLine() {
		return subBusinessLineRepo.findAll();
	}

	@Override
	public Object listSubBusinesslineByMarketOffering(UUID marketOfferingId, boolean dropdownRequested) {
		List<SubBusinessLine> subBusinessLineList= subBusinessLineRepo.listByMarketOffering(marketOfferingId);
		if(dropdownRequested) {
			List<Map<String, Object>> subBusinessLineDropDownCollector = new ArrayList<>();
			for(SubBusinessLine subBusinessLine: subBusinessLineList) {
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.ID, subBusinessLine.getId());
				map.put(CommonConstants.LABEL, subBusinessLine.getSubBusinessLine());
				subBusinessLineDropDownCollector.add(map);
			}
			return subBusinessLineDropDownCollector;
		}
		return subBusinessLineList;
	}

}
