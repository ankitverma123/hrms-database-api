package com.exalink.hrmsdatabaseapi.service;

import org.springframework.stereotype.Service;

import com.exalink.hrmsdatabaseapi.BaseException;

/**
 * @author ankitkverma
 *
 */
@Service
public interface ISubCompetencyService {
	Object listSubCompetency(Long competency, boolean requestForDropDown)throws BaseException;
}
