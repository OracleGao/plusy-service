package org.pplm.plusy.bean.scrapyd;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public class JobsBean extends ScrapydBean {
	
	private List<JobBean> pending;
	private List<JobBean> running;
	private List<JobBean> finished;
	
	public JobsBean() {
		super();
	}

	public List<JobBean> getPending() {
		return pending;
	}

	public void setPending(List<JobBean> pending) {
		this.pending = pending;
	}

	public List<JobBean> getRunning() {
		return running;
	}

	public void setRunning(List<JobBean> running) {
		this.running = running;
	}

	public List<JobBean> getFinished() {
		return finished;
	}

	public void setFinished(List<JobBean> finished) {
		this.finished = finished;
	}
	
	public boolean pendingContain(String jobId) {
		return contain(jobId, pending);
	}
	
	public boolean runningContain(String jobId) {
		return contain(jobId, running);
	}
	
	public boolean finishedContain(String jobId) {
		return contain(jobId, finished);
	}
	
	private boolean contain(String jobId, List<JobBean> jobBeans) {
		return jobBeans.stream().anyMatch(jobBean -> jobId.equals(jobBean.getId()));
	}
	
	public static class JobBean {
		
		private String id;
		private String project;
		
		@JsonAlias("start_time")
		private String startTime;
		
		@JsonAlias("end_time")
		private String endTime;

		public JobBean() {
			super();
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getProject() {
			return project;
		}

		public void setProject(String project) {
			this.project = project;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
	}
	
}
