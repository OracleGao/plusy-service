package org.pplm.plusy.utils;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public final class Constant {
	
	public static final SystemInfo SYSTEM_INFO = new SystemInfo();
	
	public static final TimeUnit SCHEDULE_TIME_UNIT = TimeUnit.MINUTES;
	
	public static final TimeUnit CHECK_TIME_UNIT = TimeUnit.SECONDS;
	
	static class SystemInfo {
		public String name = "Plusy Service";
		public String version = "0.8.0.0";
		public String startup = new Timestamp(System.currentTimeMillis()).toString();

		public SystemInfo() {
			super();
		}
	}

}
