package org.pplm.plusy.service;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.pplm.plusy.bean.SpiderConfigBean;
import org.pplm.plusy.dao.SpiderDao;
import org.pplm.plusy.lib.SpiderStartupWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SpiderScheduleService {

	@Autowired
	private SpiderDao spiderDao;

	@Autowired
	private ScrapydService scrapydService;

	private List<SpiderConfigBean> spiders;

	@Value("${plusy.threadpool.size}")
	private int threadpoolSize;

	private ScheduledExecutorService scheduledExecutorService;

	@PostConstruct
	public void init() {
		scheduledExecutorService = new ScheduledThreadPoolExecutor(threadpoolSize);
		this.spiders = this.spiderDao.getAll();
		startup();
	}

	public void startup() {
		spiders.forEach(spiderBean -> scheduledExecutorService.schedule(
				new SpiderStartupWorker(spiderBean, scheduledExecutorService, scrapydService),
				spiderBean.getRandomDelayLimit(), TimeUnit.SECONDS));
	}

}
