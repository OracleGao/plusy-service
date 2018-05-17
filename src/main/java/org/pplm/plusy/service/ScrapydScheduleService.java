package org.pplm.plusy.service;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.pplm.plusy.bean.SpiderBean;
import org.pplm.plusy.dao.SpiderDao;
import org.pplm.plusy.lib.SpiderStartupWorker;
import org.pplm.plusy.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ScrapydScheduleService {

	@Autowired
	private SpiderDao spiderDao;

	private List<SpiderBean> spiders;

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
				new SpiderStartupWorker(spiderBean, scheduledExecutorService), spiderBean.getRandomDelayLimit(),
				Constant.SCHEDULE_TIME_UNIT));
	}

}
