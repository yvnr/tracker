package com.jobboard.tracker.models;

import java.util.ArrayList;

public class JobApplicationRecords {

	private long batchSize;
	private long totalNumberOfApplications;
	private Long batchBeginId;
	private Long batchEndId;
	private Long applicationsBeginId;
	private Long applicationsEndId;
	private ArrayList<JobApplication> jobApplications;
	
	public JobApplicationRecords() {
	}
	
	public JobApplicationRecords(long batchSize, long totalNumberOfApplications, Long batchBeginId, Long batchEndId,
			Long applicationsBeginId, Long applicationsEndId, ArrayList<JobApplication> jobApplications) {
		super();
		this.batchSize = batchSize;
		this.totalNumberOfApplications = totalNumberOfApplications;
		this.batchBeginId = batchBeginId;
		this.batchEndId = batchEndId;
		this.applicationsBeginId = applicationsBeginId;
		this.applicationsEndId = applicationsEndId;
		this.jobApplications = jobApplications;
	}
	public long getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(long batchSize) {
		this.batchSize = batchSize;
	}
	public long getTotalNumberOfApplications() {
		return totalNumberOfApplications;
	}
	public void setTotalNumberOfApplications(long totalNumberOfApplications) {
		this.totalNumberOfApplications = totalNumberOfApplications;
	}
	public Long getBatchBeginId() {
		return batchBeginId;
	}
	public void setBatchBeginId(Long batchBeginId) {
		this.batchBeginId = batchBeginId;
	}
	public Long getBatchEndId() {
		return batchEndId;
	}
	public void setBatchEndId(Long batchEndId) {
		this.batchEndId = batchEndId;
	}
	public Long getApplicationsBeginId() {
		return applicationsBeginId;
	}
	public void setApplicationsBeginId(Long applicationsBeginId) {
		this.applicationsBeginId = applicationsBeginId;
	}
	public Long getApplicationsEndId() {
		return applicationsEndId;
	}
	public void setApplicationsEndId(Long applicationsEndId) {
		this.applicationsEndId = applicationsEndId;
	}
	public ArrayList<JobApplication> getJobApplications() {
		return jobApplications;
	}
	public void setJobApplications(ArrayList<JobApplication> jobApplications) {
		this.jobApplications = jobApplications;
	}
	
	
}
