package org.pplm.plusy.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.pplm.plusy.bean.scrapyd.ItemBean;
import org.pplm.plusy.bean.scrapyd.ScheduleBean;
import org.pplm.plusy.bean.scrapyd.SpidersBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ScrapydService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${plusy.scrapyd.url}")
	private String scrapydUrl;
	
	private String scheduleUrl;
	private String spidersUrlTemplate;
	private String itemsUrlTemplate;
	
	@PostConstruct
	private void init() {
		this.scheduleUrl = scrapydUrl + "/schedule.json";
		this.spidersUrlTemplate = scrapydUrl + "/listspiders.json?project=%s";
		this.itemsUrlTemplate = scrapydUrl + "/items/%s/%s/%s.jl";
	}
	
	public ScheduleBean schedule(String project, String spider) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
		map.add("project", project);
		map.add("spider", spider);

		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<ScheduleBean> re = restTemplate.postForEntity(scheduleUrl , httpEntity, ScheduleBean.class);
		ScheduleBean scheduleBean = re.getBody();
		return scheduleBean;
	}

	public SpidersBean getSpiders(String project) {
		String spidersUrl = String.format(this.spidersUrlTemplate, project);
		ResponseEntity<SpidersBean> re = restTemplate.getForEntity(spidersUrl, SpidersBean.class);
		SpidersBean spiderBean = re.getBody();
		return spiderBean;
	}
	
	public List<ItemBean> getItems(String project, String spider, String jobId) throws JsonParseException, JsonMappingException, IOException {
		String itemUrl = String.format(this.itemsUrlTemplate, project, spider, jobId);
		ResponseEntity<String> re = restTemplate.getForEntity(itemUrl, String.class);
		List<ItemBean> items = objectMapper.readValue(re.getBody(), new TypeReference<List<ItemBean>>(){});
		return items;
	}
	
}
