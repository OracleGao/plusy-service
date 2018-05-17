package org.pplm.plusy.lib;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.pplm.plusy.bean.SpiderBean;

public class SpiderStartupWorker implements Runnable {

	private SpiderBean spiderBean;
	private ScheduledExecutorService scheduledExecutorService; 
	
	public SpiderStartupWorker(SpiderBean spiderBean, ScheduledExecutorService scheduledExecutorService) {
		super();
		this.spiderBean = spiderBean;
		this.scheduledExecutorService = scheduledExecutorService;
	}

	@Override
	public void run() {
		scheduledExecutorService.schedule(this, getNextDeply(), TimeUnit.MILLISECONDS);
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

	public SpiderBean getSpiderBean() {
		return spiderBean;
	}

	public void setSpiderBean(SpiderBean spiderBean) {
		this.spiderBean = spiderBean;
	}

}
