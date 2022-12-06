package com.jobboard.tracker.services;


import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobboard.tracker.mappers.JobApplicationMapper;
import com.jobboard.tracker.models.JobApplication;
import com.jobboard.tracker.models.JobApplicationRecords;
import com.jobboard.tracker.validation.ValidationService;

@Service
public class JobApplicationService {

	private Logger logger = LogManager.getLogger(JobApplicationService.class);
			
	@Autowired
	JobApplicationMapper jobApplicationMapper;
	
	@Autowired
	ValidationService validationService;
	
	public void persistJobApplication(JobApplication jobApplication) {
		
		jobApplicationMapper.addNewApplication(jobApplication);
		logger.info("New Job Application added Successfully for user: {} from school: {}", jobApplication.getUser(), jobApplication.getSchool());
		
		return;
		
	}
	
	public JobApplication updateExistingApplication(JobApplication jobApplication) {
		
		long recordIdToUpdate = jobApplication.getId();
		validationService.validateId(recordIdToUpdate);
		logger.info("JobApplication with id: {} verified for existance successfully", jobApplication.getId());
		
		jobApplicationMapper.updateJobApplication(jobApplication);
		logger.info("JobApplication with id: {} updated successfully", jobApplication.getId());

		return jobApplication;
	}
	
	public void deleteJobApplication(long id) {
		validationService.validateId(id);
		logger.info("JobApplication with id: {} verified for existance successfully", id);

		jobApplicationMapper.deleteJobApplicationWithId(id);
		logger.info("JobApplication with id: {} deleted successfully", id);
	}
	
	public JobApplicationRecords fetchjobApplications(long startId, long numberOfRecords) {
		JobApplicationRecords jobApplications = new JobApplicationRecords();
		
		ArrayList<JobApplication> fetchedRecords = jobApplicationMapper.fetchJobApplications(startId, numberOfRecords);
		logger.info("Fetched {} Job Application from DataBase", fetchedRecords.size());
		
		jobApplications.setJobApplications(fetchedRecords);
		jobApplications.setApplicationsCount(fetchedRecords.size());
		jobApplications.setTotalNumberOfApplications(jobApplicationMapper.fetchJobApplicationsCount());
		
		
		return jobApplications;
	}
}
