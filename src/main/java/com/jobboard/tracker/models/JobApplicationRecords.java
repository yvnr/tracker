package com.jobboard.tracker.models;

import java.util.ArrayList;

/**
 * A Java class to respond to API request. It contains some job applications and its meta-data.
 *
 */
public class JobApplicationRecords {

	private long batchSize;	// number of records in this batch
	private long totalNumberOfApplications;	// total number or applications in database
	private Long batchBeginId; // the start id of the job application in jobApplications list
	private Long batchEndId;	// the highest id of the job application in jobApplications list
	private Long applicationsBeginId; // the id of the oldest job application in the database
	private Long applicationsEndId;	// the id of the latest job application in the database
	private ArrayList<JobApplication> jobApplications; // the list of job applications
	
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
