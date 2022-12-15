package com.jobboard.tracker.models;

import java.util.Date;

import com.jobboard.tracker.enums.JobStatusEnum;


public class JobApplicationAsEntity {



    private Long id;
	private String userId;
	private String univId;
	private String company;
	private String position;
	private String status; 
	private String jobId;
	private String location;
	private Date appliedTime;
	private Date assessmentTime;
	private Date interviewTime;
	private Date responseTime;
	
	public JobApplicationAsEntity() {
	}
	
	public JobApplicationAsEntity(Long id, String userId, String univId, String company, String position, String status,
			String jobId, String location, Date appliedTime, Date assessmentTime, Date interviewTime,
			Date responseTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.univId = univId;
		this.company = company;
		this.position = position;
		this.status = status;
		this.jobId = jobId;
		this.location = location;
		this.appliedTime = appliedTime;
		this.assessmentTime = assessmentTime;
		this.interviewTime = interviewTime;
		this.responseTime = responseTime;
	}

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnivId() {
		return univId;
	}

	public void setUnivId(String univId) {
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
