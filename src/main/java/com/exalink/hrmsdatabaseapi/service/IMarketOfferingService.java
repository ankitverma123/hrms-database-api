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
	List<MarketOffering> listAllMarketOffering();
	List<SubBusinessLine> listAllSubBusinessLine();
	Object listSubBusinesslineByMarketOffering(Long marketOfferingId, boolean dropdownRequested);
}
