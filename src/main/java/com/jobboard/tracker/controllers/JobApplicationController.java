package com.jobboard.tracker.controllers;


import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobboard.tracker.exceptions.DuplicateApplicationException;
import com.jobboard.tracker.exceptions.NoJobApplicationException;
import com.jobboard.tracker.models.JobApplication;
import com.jobboard.tracker.models.JobApplicationRecords;
import com.jobboard.tracker.services.JobApplicationService;
import com.sun.istack.NotNull;

@RestController
public class JobApplicationController {

	private final Logger logger = LogManager.getLogger(JobApplicationController.class);
	
	@Autowired
	JobApplicationService jobApplicationService;
	
	@PostMapping("/application")
	public ResponseEntity addNewJobApplication(	@Validated @NotNull @NotBlank @RequestBody JobApplication jobApplication, 
												@RequestHeader("X-user_id") @Validated @NotNull long userId, 
												@RequestHeader("X-univ_id") @Validated @NotNull long univId){
		
		try{
			logger.info("Recieved request to add new job application for user: {} from school: {}", jobApplication.getUserId(), jobApplication.getUnivId());
			jobApplication.setUserId(userId);
			jobApplication.setUnivId(univId);
			return new ResponseEntity(jobApplication, HttpStatus.CREATED);
		}
		catch (DuplicateApplicationException e) {
			logger.error("Unexpected erorr occured while adding new Job application, errorMessage: {}", e.getMessage());
			HashMap<String, Object> errorMap = new HashMap();
			errorMap.put("errorMessage", e.getMessage());
			errorMap.put("timeStamp", LocalDateTime.now());
			
			return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
		}
		catch (Exception ex) {
			logger.error("Unexpected erorr occured while adding new Job application, errorMessage: {}", ex.getMessage());
			HashMap<String, Object> errorMap = new HashMap();
			errorMap.put("errorMessage", ex.getMessage());
			errorMap.put("timeStamp", LocalDateTime.now());
			
			return new ResponseEntity(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/application/{applicationId}")
	public ResponseEntity updateJobApplication(	@Validated @NotNull @NotBlank @RequestBody JobApplication jobApplication,
												@PathVariable @Validated @NotBlank @NotNull long applicationId,
												@RequestHeader("X-user_id") @Validated @NotNull long userId, 
												@RequestHeader("X-univ_id") @Validated @NotNull long univId) {
		
		try{
			logger.info("Received timestamp is: " + jobApplication.getTime().toString());
			logger.info("Received request to update Job Application record with Id: {}", jobApplication.getId());
			
			jobApplication.setUserId(userId);
			jobApplication.setUnivId(univId);
			jobApplication.setId(applicationId);
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
	
	@DeleteMapping("/application/{id}")	
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
	
	
	@GetMapping("/application")
	public ResponseEntity fetchJobApplications(	@RequestParam @Min(1) long startId, @RequestParam @Max(200) long numberOfRecords,
												@RequestHeader("X-user_id") @Validated @NotNull long userId, 
												@RequestHeader("X-univ_id") @Validated @NotNull long univId) {
		
		try {
			logger.info("Received request to fetch a maximum of {} Job Applications from id: {}", numberOfRecords, startId);

			JobApplicationRecords jobApplications =  jobApplicationService.fetchjobApplications(startId, numberOfRecords, userId, univId);
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
