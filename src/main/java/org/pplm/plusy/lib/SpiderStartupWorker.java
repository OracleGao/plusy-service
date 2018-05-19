package org.pplm.plusy.lib;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.pplm.plusy.bean.SpiderConfigBean;
import org.pplm.plusy.bean.scrapyd.ScheduleBean;
import org.pplm.plusy.service.ScrapydService;

public class SpiderStartupWorker implements Runnable {

	private SpiderConfigBean spiderBean;
	private ScheduledExecutorService scheduledExecutorService;
	private ScrapydService scrapydService;

	public SpiderStartupWorker(SpiderConfigBean spiderBean, ScheduledExecutorService scheduledExecutorService,
			ScrapydService scrapydService) {
		super();
		this.spiderBean = spiderBean;
		this.scheduledExecutorService = scheduledExecutorService;
		this.scrapydService = scrapydService;
	}

	@Override
	public void run() {
		try {
			ScheduleBean scheduleBean = scrapydService.startup(spiderBean.getSpider());
			if (scheduleBean != null && !"".equals(scheduleBean.getJobId())) {
				scheduledExecutorService.schedule(
						new SpiderResultChecker(spiderBean, scheduleBean, scheduledExecutorService, scrapydService),
						spiderBean.getCheckInterval(), TimeUnit.MILLISECONDS);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		scheduledExecutorService.schedule(this, getNextDeply(), TimeUnit.SECONDS);
	}

	private long getNextDeply() {
		Long randomDelayLimit = spiderBean.getRandomDelayLimit();
		Long delay = 0L;
		if (randomDelayLimit != null && randomDelayLimit != 0) {
			delay = new Double(Math.random() * randomDelayLimit).longValue() + 1;
		}
		System.out.println(spiderBean.getInterval() + delay);
		return spiderBean.getInterval() + delay;
	}

	public ScheduledExecutorService getScheduledExecutorService() {
		return scheduledExecutorService;
	}

	public void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
	}

	public SpiderConfigBean getSpiderBean() {
		return spiderBean;
	}

	public void setSpiderBean(SpiderConfigBean spiderBean) {
		this.spiderBean = spiderBean;
	}

}
