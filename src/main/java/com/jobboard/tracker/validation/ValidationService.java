package com.jobboard.tracker.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobboard.tracker.entities.JobApplicationEntity;
import com.jobboard.tracker.exceptions.NoJobApplicationException;
import com.jobboard.tracker.mappers.JobApplicationMapper;
import com.jobboard.tracker.models.JobApplication;
import com.jobboard.tracker.models.JobApplicationAsEntity;

/**
 * A Utility class containing business logic to validate the job applications based on corresponding parameters
 *
 */
@Service
public class ValidationService {

	@Autowired
	JobApplicationMapper jobApplicationMapper;
	
	/**
	 * 
	 * Validates if a job application already exists in database with given id.
	 * @param id The unique id of the job application to serach for/
	 * @return returns the job application from database with given id.
	 */
	public JobApplicationAsEntity validateId(long id) {
		JobApplicationAsEntity jobApplicationInDB = jobApplicationMapper.findApplicationById(id);
		if(jobApplicationInDB == null)
			throw new NoJobApplicationException("No Job Application found with id: " + id);
		return jobApplicationInDB;
	}
	
	/**
	 * Validates if a given job application already exists in database
	 * @param jobApplication The job application to search
	 * @return returns true if job application already exists in database, false otherwise.
	 */
	public boolean checkIfApplicationExist(JobApplication jobApplication) {
		Long numEntries = 0L;
		numEntries = jobApplicationMapper.findApplicationByUniqueIdentifiers(jobApplication);
		if(numEntries > 0)
			
			return true;
		return false;
	}
	
	/**
	 * Validates if job applications exists by jobApplicationAsEntity
	 * @param jobApplication The job application to search for
	 * @return returns true if job application already exists in database, false otherwise. 
	 */
	public boolean checkIfApplicationExist(JobApplicationAsEntity jobApplication) {
		int numEntries = 0;
		numEntries = jobApplicationMapper.findApplicationByUniqueIdentifiersWithEntity(jobApplication);
		if(numEntries > 0)
			
			return true;
		return false;
	}
}
