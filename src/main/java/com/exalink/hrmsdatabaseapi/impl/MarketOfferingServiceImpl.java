package com.exalink.hrmsdatabaseapi.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.entity.market.MarketOffering;
import com.exalink.hrmsdatabaseapi.entity.market.SubBusinessLine;
import com.exalink.hrmsdatabaseapi.repository.IMarketOfferingBusinessLineRepository;
import com.exalink.hrmsdatabaseapi.repository.IMarketOfferingRepository;
import com.exalink.hrmsdatabaseapi.service.IMarketOfferingService;

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

}
