package com.jobboard.tracker.models;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobApplicationRecords {

	private long applicationsCount;
	private long totalNumberOfApplications;
	private ArrayList<JobApplication> jobApplications;
	
}
