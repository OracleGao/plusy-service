package org.pplm.plusy.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.pplm.plusy.bean.scrapyd.ItemBean;
import org.pplm.plusy.bean.scrapyd.ScheduleBean;
import org.pplm.plusy.bean.scrapyd.SpidersBean;
import org.pplm.plusy.service.ScrapydService;
import org.pplm.plusy.utils.ResHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping(path = "/scrapyd", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScrapydController {
	
	@Autowired
	private ScrapydService scrapydService;
	
	@GetMapping(path = "/schedule")
	public Map<String, Object> scheduleOnPost() {
		ScheduleBean scheduleBean = scrapydService.schedule("plusy", "www.cfs.gov.hk");
		return ResHelper.success(scheduleBean);
	}
	
	@GetMapping(path = "/spiders")
	public Map<String, Object> spidersOnGet() {
		SpidersBean spiderBean = scrapydService.getSpiders("plusy");
		return ResHelper.success(spiderBean);
	}
	
	@GetMapping(path = "/items")
	public Map<String, Object> itemsOnGet() throws JsonParseException, JsonMappingException, IOException {
		List<ItemBean> items = scrapydService.getItems("plusy", "www.cfs.gov.hk", "f713c506464911e8962a0242ac1c0002");
		return ResHelper.success(items);
	}
	
}
