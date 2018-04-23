package org.pplm.plusy.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.pplm.plusy.bean.DataBean;
import org.pplm.plusy.bean.scrapyd.ItemBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class DataService {

	@Autowired
	private ScrapydService scrapydService;

	public List<DataBean> getSortedDatas(String project, String spider, String jobId) throws JsonParseException, JsonMappingException, IOException {
		List<ItemBean> items = scrapydService.getItems(project, spider, jobId);
		List<DataBean> datas = items.stream().map(itemBean -> new DataBean(itemBean))
				.sorted((i1, i2) -> i2.getRowId().compareTo(i1.getRowId())).collect(Collectors.toList());
		return datas;
	}
	
	public void processDatas(String project, String spider, String jobId) {
		
	}
	
}
