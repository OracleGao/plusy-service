package org.pplm.plusy.lib;

import java.util.concurrent.ScheduledExecutorService;

import org.pplm.plusy.bean.SpiderBean;

public class SpiderResultCherker implements Runnable {
	
	private SpiderBean spiderBean;
	private ScheduledExecutorService scheduledExecutorService; 

	public SpiderResultCherker(SpiderBean spiderBean, ScheduledExecutorService scheduledExecutorService) {
		super();
		this.spiderBean = spiderBean;
		this.scheduledExecutorService = scheduledExecutorService;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public SpiderBean getSpiderBean() {
		return spiderBean;
	}

	public void setSpiderBean(SpiderBean spiderBean) {
		this.spiderBean = spiderBean;
	}

	public ScheduledExecutorService getScheduledExecutorService() {
		return scheduledExecutorService;
	}

	public void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
	}

}
