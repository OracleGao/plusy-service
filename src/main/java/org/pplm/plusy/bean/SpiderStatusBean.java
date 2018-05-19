package org.pplm.plusy.bean;

public class SpiderStatusBean {
	private String spider;
	private String jobId;
	private String status;
	private String timestamp;
	
	public SpiderStatusBean() {
		super();
	}

	public String getSpider() {
		return spider;
	}

	public void setSpider(String spider) {
		this.spider = spider;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
