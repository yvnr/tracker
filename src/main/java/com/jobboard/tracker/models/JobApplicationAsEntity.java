package com.jobboard.tracker.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobboard.tracker.enums.JobStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	
}
