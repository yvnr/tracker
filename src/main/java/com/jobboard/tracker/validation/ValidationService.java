package com.jobboard.tracker.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobboard.tracker.entities.JobApplicationEntity;
import com.jobboard.tracker.exceptions.NoJobApplicationException;
import com.jobboard.tracker.mappers.JobApplicationMapper;
import com.jobboard.tracker.models.JobApplication;
import com.jobboard.tracker.models.JobApplicationAsEntity;

@Service
public class ValidationService {

	@Autowired
	JobApplicationMapper jobApplicationMapper;
	
	public JobApplicationAsEntity validateId(long id) {
		JobApplicationAsEntity jobApplicationInDB = jobApplicationMapper.findApplicationById(id);
		if(jobApplicationInDB == null)
			throw new NoJobApplicationException("No Job Application found with id: " + id);
		return jobApplicationInDB;
	}
	
	public boolean checkIfApplicationExist(JobApplication jobApplication) {
		Long numEntries = 0L;
		numEntries = jobApplicationMapper.findApplicationByUniqueIdentifiers(jobApplication);
		if(numEntries > 0)
			
			return true;
		return false;
	}
	
	public boolean checkIfApplicationExist(JobApplicationAsEntity jobApplication) {
		int numEntries = 0;
		numEntries = jobApplicationMapper.findApplicationByUniqueIdentifiersWithEntity(jobApplication);
		if(numEntries > 0)
			
			return true;
		return false;
	}
}
