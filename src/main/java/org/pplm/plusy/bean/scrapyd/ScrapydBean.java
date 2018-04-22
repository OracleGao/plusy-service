package org.pplm.plusy.bean.scrapyd;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ScrapydBean {
	private String status;
	
	@JsonAlias("node_name")
	private String nodeName;

	public ScrapydBean() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
}
