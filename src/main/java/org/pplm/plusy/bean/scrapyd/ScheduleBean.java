package org.pplm.plusy.bean.scrapyd;

public class ScheduleBean extends ScrapydBean {
	private String jobid;

	public ScheduleBean() {
		super();
	}

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}
	
}
