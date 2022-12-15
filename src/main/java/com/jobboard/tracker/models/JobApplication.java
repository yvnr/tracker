package com.jobboard.tracker.models;

import java.util.Date;

import com.jobboard.tracker.enums.JobStatusEnum;


public class JobApplication { 
	
	private long id;
	private long userId;
	private long univId;
	private String company;
	private String position;
	private JobStatusEnum status;
	private String jobId;
	private String location;
	private Date time;
	
	public void constructFromEntity(JobApplicationAsEntity jobEntity) {
		this.id = jobEntity.getId();
		this.userId = jobEntity.getUserId();
		this.univId = jobEntity.getUnivId();
		this.company = jobEntity.getCompany();
		this.position = jobEntity.getPosition();
		this.status = JobStatusEnum.valueOf(jobEntity.getStatus());
		this.jobId = jobEntity.getJobId();
		this.location = jobEntity.getLocation();
		setTimeEntity(jobEntity);
	}
	
	private void setTimeEntity(JobApplicationAsEntity jobEntity) {
		if(this.status.equals(JobStatusEnum.APPLIED))
			time = jobEntity.getAppliedTime();
		else if(this.status.equals(JobStatusEnum.ASSESSMENT))
			time = jobEntity.getAssessmentTime();
		else if(this.status.equals(JobStatusEnum.INTERVIEW))
			time = jobEntity.getInterviewTime();
		else if(this.status.equals(JobStatusEnum.SELECTED))
			time = jobEntity.getResponseTime();
		else if(this.status.equals(JobStatusEnum.REJECTED))
			time = jobEntity.getResponseTime();
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUnivId() {
		return univId;
	}

	public void setUnivId(long univId) {
		this.univId = univId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public JobStatusEnum getStatus() {
		return status;
	}

	public void setStatus(JobStatusEnum status) {
		this.status = status;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
