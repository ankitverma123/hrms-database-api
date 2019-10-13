package com.exalink.hrmsdatabaseapi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.entity.competency.SubCompetency;
import com.exalink.hrmsdatabaseapi.repository.ISubCompetencyRepository;
import com.exalink.hrmsdatabaseapi.service.ISubCompetencyService;
import com.exalink.hrmsfabric.common.BaseException;
import com.exalink.hrmsfabric.common.CommonConstants;

/**
 * @author ankitkverma
 *
 */
@Service
public class SubCompetencyImpl implements ISubCompetencyService{

	@Autowired
	private ISubCompetencyRepository subCompetencyRepo;
	
	@Override
	public Object listSubCompetency(UUID competency, boolean requestForDropDown)throws BaseException {
		List<SubCompetency> subCompetencyList = subCompetencyRepo.listByCompetency(competency);
		if(requestForDropDown) {
			List<Map<String, Object>> subCompetencyDropDownCollector = new ArrayList<>();
			for(SubCompetency subCompetency: subCompetencyList) {
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.ID, subCompetency.getId());
				map.put(CommonConstants.LABEL, subCompetency.getSubCompetency());
				subCompetencyDropDownCollector.add(map);
			}
			return subCompetencyDropDownCollector;
		}
		return subCompetencyList;
	}

}
