package com.jobboard.tracker.models;

import java.util.Date;

import com.jobboard.tracker.entities.JobApplicationEntity;
import com.jobboard.tracker.enums.JobStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	
	public void constructFromEntity(JobApplicationEntity jobEntity) {
		this.id = jobEntity.getId();
		this.userId = jobEntity.getUserId();
		this.univId = jobEntity.getUnivId();
		this.company = jobEntity.getCompany();
		this.position = jobEntity.getPosition();
		this.status = JobStatusEnum.valueOf(jobEntity.getStatus());
		this.jobId = jobEntity.getJobId();
		this.location = jobEntity.getLocation();
		setTime(jobEntity);
	}
	
	private void setTime(JobApplicationEntity jobEntity) {
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
}
