package com.jobboard.tracker.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobboard.tracker.exceptions.NoJobApplicationException;
import com.jobboard.tracker.mappers.JobApplicationMapper;
import com.jobboard.tracker.models.JobApplication;

@Service
public class ValidationService {

	@Autowired
	JobApplicationMapper jobApplicationMapper;
	
	public void validateId(long id) {
		JobApplication jobApplicationInDB = jobApplicationMapper.findApplicationById(id);
		if(jobApplicationInDB == null)
			throw new NoJobApplicationException("No Job Application found with id: " + id);
		return;
	}
}
