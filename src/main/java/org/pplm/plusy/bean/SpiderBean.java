package org.pplm.plusy.bean;

public class SpiderBean {
	
	private String name;
	private String spider;
	private String href;
	private Long interval;
	private Long randomDelayLimit;
	private Long checkInterval;
	private Integer checkTimes;

	public SpiderBean() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpider() {
		return spider;
	}

	public void setSpider(String spider) {
		this.spider = spider;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Long getInterval() {
		return interval;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

	public Long getRandomDelayLimit() {
		return randomDelayLimit;
	}

	public void setRandomDelayLimit(Long randomDelayLimit) {
		this.randomDelayLimit = randomDelayLimit;
	}

	public Long getCheckInterval() {
		return checkInterval;
	}

	public void setCheckInterval(Long checkInterval) {
		this.checkInterval = checkInterval;
	}

	public Integer getCheckTimes() {
		return checkTimes;
	}

	public void setCheckTimes(Integer checkTimes) {
		this.checkTimes = checkTimes;
	}

}
