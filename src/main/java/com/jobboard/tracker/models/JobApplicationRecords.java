package com.jobboard.tracker.models;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobApplicationRecords {

	private long batchSize;
	private long totalNumberOfApplications;
	private Long batchBeginId;
	private Long batchEndId;
	private Long applicationsBeginId;
	private Long applicationsEndId;
	private ArrayList<JobApplication> jobApplications;
	
}
