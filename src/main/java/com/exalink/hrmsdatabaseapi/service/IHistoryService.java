package com.exalink.hrmsdatabaseapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.entity.History;

/**
 * @author ankitkverma
 *
 */
@Service
public interface IHistoryService {
	History saveHistory(String entityId, String className, String action, String role, String comment);
	List<History> listHistory(String entityId, String className);
}
