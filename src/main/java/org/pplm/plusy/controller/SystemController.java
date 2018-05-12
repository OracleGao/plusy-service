package org.pplm.plusy.controller;

import java.util.Map;

import org.pplm.plusy.utils.Constant;
import org.pplm.plusy.utils.ResHelper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class SystemController {

	@GetMapping
	public Map<String, Object> getVersion() {
		return ResHelper.success(Constant.SYSTEM_INFO);
	}
	
}
