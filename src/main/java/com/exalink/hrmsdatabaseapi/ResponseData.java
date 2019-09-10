package com.exalink.hrmsdatabaseapi;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ankitkverma
 *
 */
@Data
@AllArgsConstructor
public class ResponseData {
	private Object data;
	private String message;
	private HttpStatus status;
	private Map<String, Object> metadata;
}
