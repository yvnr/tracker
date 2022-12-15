package com.jobboard.tracker.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity(name = "job_applications_table")
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueApplicationConstraint", columnNames = {"user_id", "univ_id", "company", "job_id"})})
public class JobApplicationEntity {


	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "user_id", length = 100, nullable = false, unique = false)
	private String userId;
	
	@Column(name = "univ_id", length = 100, nullable = false, unique = false)
	private String univId;
	
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
