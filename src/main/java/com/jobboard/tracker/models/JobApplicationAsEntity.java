package com.jobboard.tracker.models;

import java.util.Date;

import com.jobboard.tracker.enums.JobStatusEnum;


public class JobApplicationAsEntity {



    private Long id;
	private long userId;
	private long univId;
	private String company;
	private String position;
	private String status; 
	private String jobId;
	private String location;
	private Date appliedTime;
	private Date assessmentTime;
	private Date interviewTime;
	private Date responseTime;
	
	public JobApplicationAsEntity(JobApplication jobApplication) {
		this.id = jobApplication.getId();
		this.userId = jobApplication.getUserId();
		this.univId = jobApplication.getUnivId();
		this.company = jobApplication.getCompany();
		this.position = jobApplication.getPosition();
		this.status = jobApplication.getStatus().toString();
		this.jobId = jobApplication.getJobId();
		this.location = jobApplication.getLocation();
		setTimes(jobApplication);
			
	}
	
	private void setTimes(JobApplication jobApplication) {
		if(jobApplication.getStatus().equals(JobStatusEnum.APPLIED))
			this.appliedTime = jobApplication.getTime();
		else if(jobApplication.getStatus().equals(JobStatusEnum.ASSESSMENT))
			this.assessmentTime = jobApplication.getTime();
		else if(jobApplication.getStatus().equals(JobStatusEnum.INTERVIEW))
			this.interviewTime = jobApplication.getTime();
		else if(jobApplication.getStatus().equals(JobStatusEnum.SELECTED) || jobApplication.getStatus().equals(JobStatusEnum.REJECTED))
			this.responseTime = jobApplication.getTime();
		return;
	}
	
	private void setNextTimesToNull(int ord) {
		for(int i = ord + 1; i < JobStatusEnum.values().length; i++) {
			if(i == 0)
				appliedTime = null;
			if(i == 1)
				this.assessmentTime = null;
			if(i == 2)
				this.interviewTime = null;
			if(i >= 3)
				this.responseTime = null;
		}
	}
	public void prepareForUpdate(JobApplication updatedApplication) {
		this.company = updatedApplication.getCompany();
		this.position = updatedApplication.getPosition();
		this.jobId = updatedApplication.getJobId();
		this.location = updatedApplication.getLocation();
		
		JobStatusEnum existingStatus = JobStatusEnum.valueOf(this.status);
		setTimes(updatedApplication);
		setNextTimesToNull(updatedApplication.getStatus().ordinal());
		
		this.status = updatedApplication.getStatus().toString();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public Date getAppliedTime() {
		return appliedTime;
	}

	public void setAppliedTime(Date appliedTime) {
		this.appliedTime = appliedTime;
	}

	public Date getAssessmentTime() {
		return assessmentTime;
	}

	public void setAssessmentTime(Date assessmentTime) {
		this.assessmentTime = assessmentTime;
	}

	public Date getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(Date interviewTime) {
		this.interviewTime = interviewTime;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	
	
	
}
