package com.exalink.hrmsdatabaseapi.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.entity.History;
import com.exalink.hrmsdatabaseapi.repository.IHistoryJPARepository;
import com.exalink.hrmsdatabaseapi.service.IHistoryService;

/**
 * @author ankitkverma
 *
 */
@Service
public class HistoryServiceImpl implements IHistoryService{

	@Autowired
	private IHistoryJPARepository historyRepo;
	
	@Override
	public History saveHistory(String entityId, String className, String action, String role, String comment) {
		History history = new History();
		history.setAction(action);
		history.setClassName(className);
		history.setComment(comment);
		history.setCreatedAt(new Date());
		history.setEntityId(entityId);
		history.setRole(role);
		return historyRepo.save(history);
	}

	@Override
	public List<History> listHistory(String entityId, String className) {
		return historyRepo.findByEntityIdAndClassName(entityId, className);
	}

}
