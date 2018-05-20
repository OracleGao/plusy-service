package org.pplm.plusy.lib;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

import org.pplm.plusy.bean.SpiderConfigBean;
import org.pplm.plusy.bean.scrapyd.ScheduleBean;
import org.pplm.plusy.service.ScrapydService;
import org.pplm.plusy.utils.Constant;
import org.pplm.plusy.utils.Constant.ScrapydJobStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpiderResultChecker implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(SpiderResultChecker.class);

	private SpiderConfigBean spiderConfigBean;
	private ScheduledExecutorService scheduledExecutorService;
	private ScheduleBean scheduleBean;
	private ScrapydService scrapydService;
	private int times = 1;

	public SpiderResultChecker(SpiderConfigBean spiderConfigBean, ScheduleBean scheduleBean,
			ScheduledExecutorService scheduledExecutorService, ScrapydService scrapydService) {
		super();
		this.spiderConfigBean = spiderConfigBean;
		this.scheduleBean = scheduleBean;
		this.scheduledExecutorService = scheduledExecutorService;
		this.scrapydService = scrapydService;
	}

	@Override
	public void run() {
		try {
			String spider = spiderConfigBean.getSpider();
			String jobId = scheduleBean.getJobId();
			ScrapydJobStatus scrapydJobStatus = scrapydService.getJobStatus(jobId);
			logger.debug("===  check spider [" + spider + "], status [" + scrapydJobStatus.getValue() +  "] ===");
			scrapydService.putSpiderStatus(spider, jobId, scrapydJobStatus.getValue());
			switch (scrapydJobStatus) {
			case PENDING:
			case RUNNING:
				if (times++ >= spiderConfigBean.getCheckTimes()) {
					scrapydService.putSpiderStatus(spider, jobId, ScrapydJobStatus.TIMEOUT.getValue());
				} else {
					logger.debug("=== schedule next spider [" + spider + "] check in [" +  spiderConfigBean.getCheckInterval() + "] " + Constant.CHECK_TIME_UNIT.name() + " ===");
					scheduledExecutorService.schedule(this, spiderConfigBean.getCheckInterval(), Constant.CHECK_TIME_UNIT);
				}
				break;
			case FINISHED:
				scrapydService.processItems(spider, jobId);
				break;
			case UNKNOW:
				break;
			case TIMEOUT:
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SpiderConfigBean getSpiderConfigBean() {
		return spiderConfigBean;
	}

	public void setSpiderConfigBean(SpiderConfigBean spiderConfigBean) {
		this.spiderConfigBean = spiderConfigBean;
	}

	public ScheduleBean getScheduleBean() {
		return scheduleBean;
	}

	public void setScheduleBean(ScheduleBean scheduleBean) {
		this.scheduleBean = scheduleBean;
	}

	public ScheduledExecutorService getScheduledExecutorService() {
		return scheduledExecutorService;
	}

	public void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
	}

}
