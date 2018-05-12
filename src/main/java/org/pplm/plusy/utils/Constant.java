package org.pplm.plusy.utils;

import java.sql.Timestamp;

public final class Constant {
	
	public static final SystemInfo SYSTEM_INFO = new SystemInfo();
	
	static class SystemInfo {
		public String name = "Plusy Service";
		public String version = "0.8.0.0";
		public String startup = new Timestamp(System.currentTimeMillis()).toString();

		public SystemInfo() {
			super();
		}
	}

}
