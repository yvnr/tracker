package com.jobboard.tracker.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.jobboard.tracker.enums.JobStatusEnum;
import com.jobboard.tracker.models.JobApplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "job_applications_table")
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueApplicationConstraint", columnNames = {"user_id", "univ_id", "company", "job_id", "status"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationEntity {


	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	 
	@Column(name = "user_id", length = 100, nullable = false, unique = false)
	private long userId;
	
	@Column(name = "univ_id", length = 100, nullable = false, unique = false)
	private long univId;
	
	@Column(name = "company", length = 100, nullable = false, unique = false)
	private String company;
	
	@Column(name = "position", length = 100, nullable = false, unique = false)
	private String position;
	
	@Column(name = "status", nullable = false, unique = false)
	private String status; 
	
	@Column(name = "job_id", length = 100, nullable = false, unique = false)
	private String jobId;
	
	@Column(name = "location", length = 100, nullable = false, unique = false)
	private String location;
	
	@Column(name = "applied_time", nullable = true, unique = false)
	private Date appliedTime;
	
	@Column(name = "assessment_time", nullable = true, unique = false)
	private Date assessmentTime;
	
	@Column(name = "interview_time", nullable = true, unique = false)
	private Date interviewTime;
	
	@Column(name = "response_time", nullable = true, unique = false)
	private Date responseTime;
	
	public JobApplicationEntity(JobApplication jobApplication) {
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
