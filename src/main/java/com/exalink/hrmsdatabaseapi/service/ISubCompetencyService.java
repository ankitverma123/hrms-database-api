package com.exalink.hrmsdatabaseapi.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.exalink.hrmsfabric.common.BaseException;

/**
 * @author ankitkverma
 *
 */
@Service
public interface ISubCompetencyService {
	Object listSubCompetency(UUID competency, boolean requestForDropDown)throws BaseException;
}
