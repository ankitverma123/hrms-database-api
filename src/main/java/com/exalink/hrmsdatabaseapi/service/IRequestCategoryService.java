package com.exalink.hrmsdatabaseapi.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

/**
 * @author ankitkverma
 *
 */
@Service
public interface IRequestCategoryService {
	Object loadRequestCategory(String name, String type, String modifyEntityId, HttpServletRequest request);
}
