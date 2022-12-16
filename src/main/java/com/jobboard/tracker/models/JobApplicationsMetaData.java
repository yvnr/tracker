package com.jobboard.tracker.models;

/**
 * The Meta data of the job applications of a user in the database.
 *
 */
public class JobApplicationsMetaData {

	long totalApplicationsCount;	// total number of applications in the database
	long inProgressCount;			// total number of application which are in ASSESSMENT or INTERVIEW status in the database
	long offersCount;				// total number of applications in the database which are in SELECTED status
	
	public JobApplicationsMetaData() {
	}

	public JobApplicationsMetaData(int totalApplicationsCount, int inProgressCount, int offersCount) {
		super();
		this.totalApplicationsCount = totalApplicationsCount;
		this.inProgressCount = inProgressCount;
		this.offersCount = offersCount;
	}

	public long getTotalApplicationsCount() {
		return totalApplicationsCount;
	}

	public void setTotalApplicationsCount(long totalApplicationsCount) {
		this.totalApplicationsCount = totalApplicationsCount;
	}

	public long getInProgressCount() {
		return inProgressCount;
	}

	public void setInProgressCount(long inProgressCount) {
		this.inProgressCount = inProgressCount;
	}

	public long getOffersCount() {
		return offersCount;
	}

	public void setOffersCount(long offersCount) {
		this.offersCount = offersCount;
	}

	
}
