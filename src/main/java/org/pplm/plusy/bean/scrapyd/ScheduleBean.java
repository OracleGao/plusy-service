package org.pplm.plusy.bean.scrapyd;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ScheduleBean extends ScrapydBean {

	@JsonAlias("jobid")
	private String jobId;

	public ScheduleBean() {
		super();
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
}
