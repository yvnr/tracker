package com.jobboard.tracker.services;


import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobboard.tracker.exceptions.DuplicateApplicationException;
import com.jobboard.tracker.mappers.JobApplicationMapper;
import com.jobboard.tracker.models.JobApplication;
import com.jobboard.tracker.models.JobApplicationAsEntity;
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
		
		if(validationService.checkIfApplicationExist(jobApplication))
			throw new DuplicateApplicationException("Application already existing");
		JobApplicationAsEntity jobApplicationEntity = new JobApplicationAsEntity(jobApplication);
		
		jobApplicationMapper.addNewApplication(jobApplicationEntity);
		jobApplication.setId(jobApplicationEntity.getId());
		logger.info("New Job Application added Successfully for user: {} from school: {}", jobApplication.getUserId(), jobApplication.getUnivId());
		
		return;
		
	}
	
	public JobApplication updateExistingApplication(JobApplication jobApplication) {
		
		long recordIdToUpdate = jobApplication.getId();
		JobApplicationAsEntity jobApplicationExisting =  validationService.validateId(recordIdToUpdate);
		logger.info("JobApplication with id: {} verified for existance successfully", jobApplication.getId());
		
		jobApplicationExisting.prepareForUpdate(jobApplication);
		if(validationService.checkIfApplicationExist(jobApplicationExisting))
			throw new DuplicateApplicationException("Application with updated fields already exist");
		
		
		jobApplicationMapper.updateJobApplication(jobApplicationExisting);
		logger.info("JobApplication with id: {} updated successfully", jobApplication.getId());

		return jobApplication;
	}
	
	public void deleteJobApplication(long id) {
		validationService.validateId(id);
		logger.info("JobApplication with id: {} verified for existance successfully", id);

		jobApplicationMapper.deleteJobApplicationWithId(id);
		logger.info("JobApplication with id: {} deleted successfully", id);
	}
	
	public JobApplicationRecords fetchjobApplications(long startId, long numberOfRecords, String userId, String univId) {
		JobApplicationRecords jobApplications = new JobApplicationRecords();
		
		ArrayList<JobApplicationAsEntity> fetchedRecords = jobApplicationMapper.fetchJobApplications(startId, numberOfRecords, userId, univId);
		logger.info("Fetched {} Job Application from DataBase", fetchedRecords.size());
		ArrayList<JobApplication> recordsToShare = new ArrayList<JobApplication>();
		
		long beginId = Long.MAX_VALUE;
		long endId = Long.MIN_VALUE;
		
		// parsing to UI required format
		for(JobApplicationAsEntity currentEntity : fetchedRecords) {
			JobApplication currentApplication = new JobApplication();
			currentApplication.constructFromEntity(currentEntity);
			recordsToShare.add(currentApplication);
			
			//keeping track of begin and end Id of the applications
			
			beginId = Math.min(beginId, currentApplication.getId());
			endId = Math.max(endId, currentApplication.getId());
		}
		
		if(recordsToShare.size() > 0) {
			jobApplications.setBatchBeginId(beginId);
			jobApplications.setBatchEndId(endId);
		}
		
		Long recordsBeginId = jobApplicationMapper.getBeginId(userId, univId);
		Long recordsEndId = jobApplicationMapper.getEndId(userId, univId);
		
		jobApplications.setJobApplications(recordsToShare);
		jobApplications.setBatchSize(recordsToShare.size());
		jobApplications.setTotalNumberOfApplications(jobApplicationMapper.fetchJobApplicationsCount(userId, univId));
		jobApplications.setApplicationsBeginId(recordsBeginId);
		jobApplications.setApplicationsEndId(recordsEndId);
				
		return jobApplications;
	}
	
	
}
