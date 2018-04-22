package org.pplm.plusy.bean.scrapyd;

import java.util.List;

public class SpidersBean extends ScrapydBean {
	private List<String> spiders;

	public SpidersBean() {
		super();
	}

	public List<String> getSpiders() {
		return spiders;
	}

	public void setSpiders(List<String> spiders) {
		this.spiders = spiders;
	}

}
