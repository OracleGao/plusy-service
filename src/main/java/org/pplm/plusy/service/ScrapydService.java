package org.pplm.plusy.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.pplm.plusy.bean.SpiderStatusBean;
import org.pplm.plusy.bean.scrapyd.ItemBean;
import org.pplm.plusy.bean.scrapyd.JobsBean;
import org.pplm.plusy.bean.scrapyd.ScheduleBean;
import org.pplm.plusy.bean.scrapyd.SpidersBean;
import org.pplm.plusy.utils.Constant;
import org.pplm.plusy.utils.Constant.ScrapydJobStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ScrapydService {
	
	private static Logger logger = LoggerFactory.getLogger(ScrapydService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private DataService dataService;
	
	@Value("${plusy.scrapyd.project}")
	private String scrapydProject;
	
	@Value("${plusy.scrapyd.url}")
	private String scrapydUrl;
	
	private Map<String, SpiderStatusBean> spiderStatusMap = new HashMap<>();
	
	private String scheduleUrl;
	private String spidersUrl;
	private String itemsUrlTemplate;
	private String jobsUrl;
	
	@PostConstruct
	private void init() {
		this.scheduleUrl = scrapydUrl + "/schedule.json";
		this.spidersUrl = scrapydUrl + "/listspiders.json?project=" + scrapydProject;
		this.itemsUrlTemplate = scrapydUrl + "/items/" + scrapydProject + "/%s/%s.jl";
		this.jobsUrl = scrapydUrl + "/listjobs.json?project=" + scrapydProject;
	}
	
	public ScheduleBean startup(String spider) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
		map.add("project", scrapydProject);
		map.add("spider", spider);

		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		String json = postForString(scheduleUrl , httpEntity);
		logger.debug("=== schedule result: " + json + " ===");
		if (json != null) {
			ScheduleBean scheduleBean = objectMapper.readValue(json, ScheduleBean.class);
			if ("ok".equals(scheduleBean.getStatus())) {
				this.putSpiderStatus(spider, scheduleBean.getJobId(), "pending");
				return scheduleBean;
			} else {
				this.putSpiderStatus(spider, scheduleBean.getJobId(), "unknow");
			}
		}
		return null;
	}

	public void putSpiderStatus(String spider, String jobId, String status) {
		SpiderStatusBean spiderStatusBean = null;
		if (spiderStatusMap.containsKey(spider)) {
			spiderStatusBean = spiderStatusMap.get(spider);
			spiderStatusBean.setJobId(jobId);
			spiderStatusBean.setStatus(status);
			spiderStatusBean.setTimestamp(Constant.currentTimestamp());
		} else {
			spiderStatusBean = new SpiderStatusBean();
			spiderStatusBean.setSpider(spider);
			spiderStatusBean.setJobId(jobId);
			spiderStatusBean.setStatus(status);
			spiderStatusBean.setTimestamp(Constant.currentTimestamp());
			spiderStatusMap.put(spider, spiderStatusBean);
		}
	}
	
	public Collection<SpiderStatusBean> getSpidersStatus() {
		return spiderStatusMap.values();
	}
	
	public SpidersBean getSpiders() throws IOException {
		String json = postForString(spidersUrl);
		if (json != null) {
			return objectMapper.readValue(json, SpidersBean.class);
		}
		return null;
	}
	
	public List<ItemBean> getItems(String spider, String jobId) throws IOException {
		String itemUrl = String.format(this.itemsUrlTemplate, spider, jobId);
		String json = postForString(itemUrl);
		if (json != null) {
			return objectMapper.readValue(json, new TypeReference<List<ItemBean>>(){});
		}
		return Collections.emptyList();
	}
	
	public JobsBean getJobs() throws IOException {
		String json = postForString(jobsUrl);
		if (json != null) {
			return objectMapper.readValue(json, JobsBean.class);
		}
		return null;
	}
	
	public ScrapydJobStatus getJobStatus(String jobId) throws IOException {
		JobsBean jobsBean = getJobs();
		if (jobsBean != null) {
			if (jobsBean.pendingContain(jobId)) {
				return ScrapydJobStatus.PENDING;
			} else if (jobsBean.runningContain(jobId)) {
				return ScrapydJobStatus.PENDING;
			} else if (jobsBean.finishedContain(jobId)) {
				return ScrapydJobStatus.FINISHED;
			} else {
				return ScrapydJobStatus.UNKNOW;
			}
		}
		return ScrapydJobStatus.UNKNOW;
	}
	
	public void processItems(String spider, String jobId) throws IOException {
		List<ItemBean> items = getItems(spider, jobId);
		
		dataService.putItems(spider, items);
	}
	
	private String postForString(String url) {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			return responseEntity.getBody();
		}
		return null;
	}
	
	private String postForString(String url, HttpEntity<MultiValueMap<String, String>> httpEntity) {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url , httpEntity, String.class);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			return responseEntity.getBody();
		}
		return null;
	}
	
}
