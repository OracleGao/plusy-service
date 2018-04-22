package org.pplm.plusy.lib;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpiderScheduler implements Runnable {
	
	@Value("plusy.spider.workdir")
	private String spiderWorkdir;
	
	
	public final void startup() {
		
	}
	
	@Override
	public void run() {
		
	}

}
