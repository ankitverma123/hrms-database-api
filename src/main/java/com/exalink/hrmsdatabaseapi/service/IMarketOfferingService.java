package com.exalink.hrmsdatabaseapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.entity.market.MarketOffering;
import com.exalink.hrmsdatabaseapi.entity.market.SubBusinessLine;

/**
 * @author ankitkverma
 *
 */
@Service
public interface IMarketOfferingService {
	public List<MarketOffering> listAllMarketOffering();
	public List<SubBusinessLine> listAllSubBusinessLine();
}
