package org.pplm.plusy.utils;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class Constant {

	public static final SystemInfo SYSTEM_INFO = new SystemInfo();

	public static final TimeUnit SCHEDULE_TIME_UNIT = TimeUnit.SECONDS;

	public static final TimeUnit CHECK_TIME_UNIT = TimeUnit.SECONDS;
	
	public static final ObjectMapper objectMapper = new ObjectMapper();

	static class SystemInfo {
		public String name = "Plusy Service";
		public String version = "0.8.0.1";
		public String startup = currentTimestamp();

		public SystemInfo() {
			super();
		}
	}

	public static String currentTimestamp() {
		return new Timestamp(System.currentTimeMillis()).toString();
	}

	public static String jsonEncode(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static enum ScrapydJobStatus {
		PENDING("pending"), RUNNING("running"), FINISHED("finished"), UNKNOW("unkown"), TIMEOUT("timeout");

		private String value;

		private ScrapydJobStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

}
