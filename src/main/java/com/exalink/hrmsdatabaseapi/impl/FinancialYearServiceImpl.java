package com.exalink.hrmsdatabaseapi.impl;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.BaseException;
import com.exalink.hrmsdatabaseapi.Utils;
import com.exalink.hrmsdatabaseapi.entity.candidate.FinancialYear;
import com.exalink.hrmsdatabaseapi.repository.IFinancialYearRepository;
import com.exalink.hrmsdatabaseapi.service.IFinancialYearService;

/**
 * @author ankitkverma
 *
 */
@Service
public class FinancialYearServiceImpl implements IFinancialYearService{
	
	@Autowired
	private IFinancialYearRepository financialYearRepo;

	@Override
	public FinancialYear updateFinancialYear(Map<String, Object> request) throws BaseException{
		if(Utils.checkCollectionHasKeyAndValue(request, "id")) {
			String id = request.get("id").toString();
			String financialYear = request.get("financialYear").toString();
			Optional<FinancialYear> fyById = financialYearRepo.findById(UUID.fromString(id));
			if(fyById.isPresent()) {
				Optional<FinancialYear> fyByName = financialYearRepo.findByFinancialYear(financialYear);
				if(fyByName.isPresent()) {
					throw new BaseException(FinancialYearServiceImpl.class, financialYear+" is already being used");
				}
				FinancialYear fy = fyById.get();
				fy.setFinancialYear(financialYear);
				fy.setUpdatedAt(new Date());
				financialYearRepo.save(fy);
			} else {
				throw new BaseException(FinancialYearServiceImpl.class, "Invalid request, No financial year found for id: "+id);
			}
		}
		return null;
	}
	
	
	
}
