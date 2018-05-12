package org.pplm.plusy.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.pplm.plusy.bean.scrapyd.ItemBean;
import org.pplm.plusy.bean.scrapyd.ScheduleBean;
import org.pplm.plusy.bean.scrapyd.SpidersBean;
import org.pplm.plusy.service.DataService;
import org.pplm.plusy.service.ScrapydService;
import org.pplm.plusy.utils.ResHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping(path = "/scrapyd", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScrapydController {

	@Autowired
	private ScrapydService scrapydService;
	
	@Autowired
	private DataService dataService;

	@GetMapping(path = "/startup")
	public Map<String, Object> startupOnPost() {
		ScheduleBean scheduleBean = scrapydService.startup("www.cfs.gov.hk");
		return ResHelper.success(scheduleBean);
	}

	@GetMapping(path = "/spiders")
	public Map<String, Object> spidersOnGet() {
		SpidersBean spiderBean = scrapydService.getSpiders();
		return ResHelper.success(spiderBean);
	}

	@PostMapping(path = "/items")
	public Map<String, Object> itemsOnPost(@RequestParam(name = "spider", required = true)String spider, @RequestParam(name = "jobId", required = true) String jobId) throws JsonParseException, JsonMappingException, IOException {
		List<ItemBean> items = scrapydService.getItems(spider, jobId);
		dataService.putItems(spider, items);
		return ResHelper.success(items);
	}
	
}
