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
import com.jobboard.tracker.models.JobApplicationsMetaData;
import com.jobboard.tracker.validation.ValidationService;

/**
 * A Service layer class. The methods in this class are called by controller layers objects. 
 * contains all the business logic to add/update/delete/get job applications from database
 *
 */
@Service
public class JobApplicationService {

	private Logger logger = LogManager.getLogger(JobApplicationService.class);
			
	@Autowired
	JobApplicationMapper jobApplicationMapper;
	
	@Autowired
	ValidationService validationService;
	
	/**
	 * Validates the job application and if validated successfully adds the applications to the database
	 * @param jobApplication The job application received from the client through API request
	 */
	public void persistJobApplication(JobApplication jobApplication) {
		
		if(validationService.checkIfApplicationExist(jobApplication))
			throw new DuplicateApplicationException("Application already exists");
		logger.info("The received job application validated successfully");
		JobApplicationAsEntity jobApplicationEntity = new JobApplicationAsEntity(jobApplication);
		
		jobApplicationMapper.addNewApplication(jobApplicationEntity);
		jobApplication.setId(jobApplicationEntity.getId());
		logger.debug("New Job Application persisted to Data Base Successfully for user: {} from school: {}", jobApplication.getUserId(), jobApplication.getUnivId());
		
		return;
		
	}
	
	/**
	 * Validates the updated job application and updates the same record in the database
	 * @param jobApplication The updated values of the job application
	 * @return	 returns the updated record from the database.
	 */
	public JobApplication updateExistingApplication(JobApplication jobApplication) {
		
		long recordIdToUpdate = jobApplication.getId();
		JobApplicationAsEntity jobApplicationExisting =  validationService.validateId(recordIdToUpdate);
		logger.info("JobApplication with id: {} verified for existance successfully", jobApplication.getId());
		
		jobApplicationExisting.prepareForUpdate(jobApplication);
		if(validationService.checkIfApplicationExist(jobApplicationExisting))
			throw new DuplicateApplicationException("Application with updated fields already exists");
		
		
		jobApplicationMapper.updateJobApplication(jobApplicationExisting);
		

		return jobApplication;
	}
	
	/**
	 * contains the business logic to validate and delete a job application permanently from the database.
	 * @param id the unique id of the job application to be deleted.
	 */
	public void deleteJobApplication(long id) {
		validationService.validateId(id);
		logger.info("JobApplication with id: {} verified for existance successfully", id);

		jobApplicationMapper.deleteJobApplicationWithId(id);
		logger.info("JobApplication with id: {} deleted successfully", id);
	}
	
	/**
	 * Contains the business logic to fetch job application based on some filters
	 * @param startId	the beginning id of the job application to fetch
	 * @param numberOfRecords the maximum number of job applications to be fetched from the database
	 * @param userId The unique id of the user
	 * @param univId The unique id of the university/school
	 * @return returns the job applications fetched from the database
	 */
	public JobApplicationRecords fetchjobApplications(long startId, long numberOfRecords, String userId, String univId) {
		JobApplicationRecords jobApplications = new JobApplicationRecords();
		
		ArrayList<JobApplicationAsEntity> fetchedRecords = jobApplicationMapper.fetchJobApplications(startId, numberOfRecords, userId, univId);
		logger.debug("Fetched {} Job Application from DataBase", fetchedRecords.size());
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
		logger.debug("The job applications returning to user id: {} and school id: {} are: ", userId, univId, jobApplications.toString());
		
		return jobApplications;
	}
	
	/**
	 * Contains business logic to fetch the metadata of the job applications made by a user
	 * @param userId The unique id of the user
	 * @param univId The unique id of the university/school
	 * @return returns the job applications meta data.
	 */
	public JobApplicationsMetaData fetchUserMetaData(String userId, String univId) {
		JobApplicationsMetaData jobApplicationsMetaData = new JobApplicationsMetaData();
		
		jobApplicationsMetaData.setTotalApplicationsCount(jobApplicationMapper.fetchJobApplicationsCount(userId, univId));
		jobApplicationsMetaData.setInProgressCount(jobApplicationMapper.fetchInprogressCountByUserId(userId, univId));
		jobApplicationsMetaData.setOffersCount(jobApplicationMapper.fetchJobOffersCountByUserId(userId, univId));
		
		return jobApplicationsMetaData;
	}
	
}
