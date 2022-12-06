package com.jobboard.tracker.controllers;


import java.time.LocalDateTime;
import java.util.HashMap;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobboard.tracker.entities.JobApplicationEntity;
import com.jobboard.tracker.exceptions.NoJobApplicationException;
import com.jobboard.tracker.models.JobApplication;
import com.jobboard.tracker.models.JobApplicationRecords;
import com.jobboard.tracker.services.JobApplicationService;
import com.jobboard.tracker.validation.ValidationService;
import com.sun.istack.NotNull;

@RestController
public class JobApplicationController {

	private final Logger logger = LogManager.getLogger(JobApplicationController.class);
	
	@Autowired
	JobApplicationService jobApplicationService;
	
	@PostMapping("/job")
	public ResponseEntity addNewJobApplication(@Validated @NotNull @NotBlank @RequestBody JobApplication jobApplication){
		
		try{
			logger.info("Recieved request to add new job application for user: {} from school: {}", jobApplication.getUser(), jobApplication.getSchool());
			jobApplicationService.persistJobApplication(jobApplication);
			return new ResponseEntity(jobApplication, HttpStatus.CREATED);
		}
		catch (Exception ex) {
			logger.error("Unexpected erorr occured while adding new Job application, errorMessage: {}", ex.getMessage());
			HashMap<String, Object> errorMap = new HashMap();
			errorMap.put("errorMessage", ex.getMessage());
			errorMap.put("timeStamp", LocalDateTime.now());
			
			return new ResponseEntity(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/job")
	public ResponseEntity updateJobApplication(@Validated @NotNull @NotBlank @RequestBody JobApplication jobApplication) {
		
		try{
			logger.info("Received request to update Job Application record with Id: {}", jobApplication.getId());
			jobApplicationService.updateExistingApplication(jobApplication);
			return new ResponseEntity(jobApplication, HttpStatus.OK);
		}
		catch (NoJobApplicationException e) {
			logger.error("Error while updating job application with id: {}, errorMessage is: {}", jobApplication.getId(), e.getMessage());
			HashMap<String, Object> errorMap = new HashMap();
			errorMap.put("errorMessage", e.getMessage());
			errorMap.put("timeStamp", LocalDateTime.now());
			
			return new ResponseEntity(errorMap, HttpStatus.NOT_FOUND);
		}
		catch (Exception ex) {
			logger.error("Unexpected error occurede while updating job application with id: {}, errorMessage is: {}", jobApplication.getId(), ex.getMessage());

			HashMap<String, Object> errorMap = new HashMap();
			errorMap.put("errorMessage", ex.getMessage());
			errorMap.put("timeStamp", LocalDateTime.now());
			
			return new ResponseEntity(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@DeleteMapping("/job")
	public ResponseEntity removeJobApplication(@PathVariable long id) {
		
		try {
			logger.info("Received request to delete Job Application record with Id: {}", id);
			jobApplicationService.deleteJobApplication(id);
			
			return new ResponseEntity(HttpStatus.OK);
		}
		catch (NoJobApplicationException e) {
			logger.error("Error while deleting job application with id: {}, errorMessage is: {}", id, e.getMessage());
			HashMap<String, Object> errorMap = new HashMap();
			errorMap.put("errorMessage", e.getMessage());
			errorMap.put("timeStamp", LocalDateTime.now());
			
			return new ResponseEntity(errorMap, HttpStatus.NOT_FOUND);
		}
		catch (Exception ex) {
			logger.error("Unexpected error occurede while deleting job application with id: {}, errorMessage is: {}", id, ex.getMessage());

			HashMap<String, Object> errorMap = new HashMap();
			errorMap.put("errorMessage", ex.getMessage());
			errorMap.put("timeStamp", LocalDateTime.now());
			
			return new ResponseEntity(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/jobs")
	public ResponseEntity fetchJobApplications(@RequestParam @Min(1) long startId, @RequestParam @Max(200) long numberOfRecords) {
		
		try {
			logger.info("Received request to fetch a maximum of {} Job Applications from id: {}", numberOfRecords, startId);

			JobApplicationRecords jobApplications =  jobApplicationService.fetchjobApplications(startId, numberOfRecords);
			return new ResponseEntity(jobApplications, HttpStatus.OK);
		}
		catch (Exception e) {
			logger.error("Unexpected error occured while fetching job applications, errorMessage is: {}", e.getMessage());

			HashMap<String, Object> errorMap = new HashMap();
			errorMap.put("errorMessage", e.getMessage());
			errorMap.put("timeStamp", LocalDateTime.now());
			
			return new ResponseEntity(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
