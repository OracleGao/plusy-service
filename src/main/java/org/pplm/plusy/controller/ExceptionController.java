package org.pplm.plusy.controller;

import java.util.Map;

import javax.naming.AuthenticationException;

import org.pplm.plusy.utils.ResHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionController {
	
	private Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(Exception.class)
	public Map<String, Object> exceptionDefault(Exception e) {
		logger.error(e.getMessage(), e);
		return ResHelper.error(e.getMessage());
	}
	
	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Map<String, Object> exceptionDefault(AuthenticationException e) {
		logger.error(e.getMessage(), e);
		return ResHelper.error(HttpStatus.UNAUTHORIZED.name());
	}
	
}
