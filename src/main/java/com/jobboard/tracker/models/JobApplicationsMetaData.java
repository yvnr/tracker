package com.jobboard.tracker.models;

public class JobApplicationsMetaData {

	long totalApplicationsCount;
	long inProgressCount;
	long offersCount;
	
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
