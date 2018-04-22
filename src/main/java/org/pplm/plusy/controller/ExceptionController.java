package org.pplm.plusy.controller;

import java.util.Map;

import org.pplm.plusy.utils.ResHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	
	private Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(Exception.class)
	public Map<String, Object> exceptionDefault(Exception e) {
		logger.error(e.getMessage(), e);
		return ResHelper.error(e.getMessage());
	}
	
}
