package org.pplm.plusy.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.pplm.plusy.bean.DataBean;
import org.pplm.plusy.dao.SpiderDao;
import org.pplm.plusy.service.DataService;
import org.pplm.plusy.utils.ResHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/spider", produces = MediaType.APPLICATION_JSON_VALUE)
public class SpiderController {

	@Autowired
	private SpiderDao plusyDao;

	@Autowired
	private DataService dataService;

	@GetMapping(path = "/list")
	public Map<String, Object> listOnGet() {
		return ResHelper.success(plusyDao.getAll());
	}

	@GetMapping(path = "/datas")
	public Map<String, Object> datasOnGet(@RequestParam(name = "spider", required = true) String spider)
			throws IOException {
		List<DataBean> datas = dataService.getDatas(spider);
		return ResHelper.success(datas);
	}

	@PostMapping(path = "/read")
	public Map<String, Object> readOnPost(@RequestParam(name = "spider", required = true) String spider,
			@RequestParam(name = "rowId", required = true) String rowId) throws IOException {
		dataService.setRead(spider, rowId);
		return ResHelper.success();
	}

}
