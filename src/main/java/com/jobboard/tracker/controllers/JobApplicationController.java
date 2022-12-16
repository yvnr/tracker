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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobboard.tracker.exceptions.DuplicateApplicationException;
import com.jobboard.tracker.exceptions.NoJobApplicationException;
import com.jobboard.tracker.models.JobApplication;
import com.jobboard.tracker.models.JobApplicationRecords;
import com.jobboard.tracker.models.JobApplicationsMetaData;
import com.jobboard.tracker.services.JobApplicationService;
import com.sun.istack.NotNull;

/**
 * The class exposes all the APIs towards the user interface to add/update/delete/get job application by user. 
 * It also exposes a GET API to get the statistics of applications made by the user.
 * 
 */
@RestController
public class JobApplicationController {

	private final Logger logger = LogManager.getLogger(JobApplicationController.class);
	
	// Autowired indicates spring boot to inject the bean of specified type
	@Autowired
	JobApplicationService jobApplicationService;	// The service class
	
	/**
	 * POST API: /application exposed towards the User Interface. The API allows user to add a new job application
	 * @param jobApplication The JobApplication object containing all the details of the new application user wants to add
	 * @param userId  The unique id assigned to user by the authenticator and is sent in request header
	 * @param univId The unique id assigned to University by the authenticator and is sent in request header.
	 * @return ResponseEntity object with response body and appropriate HTTP status code.
	 */
	@PostMapping("/application")
	public ResponseEntity addNewJobApplication(	@Validated @NotNull @NotBlank @RequestBody JobApplication jobApplication, 
												@RequestHeader("x-uid") @Validated @NotNull String userId, 
												@RequestHeader("x-univ-id") @Validated @NotNull String univId){
		
		try{
			jobApplication.setUserId(userId);
			jobApplication.setUnivId(univId);
			logger.info("Recieved request to add new job application for user: {} from school: {}", jobApplication.getUserId(), jobApplication.getUnivId());
			jobApplicationService.persistJobApplication(jobApplication);
			logger.debug("The job application added for user: {} from school: {} is: {}", jobApplication.getUserId(), jobApplication.getUnivId(), jobApplication.toString());
			logger.info("Successfully added new job application for user: {} from school: {}", jobApplication.getUserId(), jobApplication.getUnivId());

			return new ResponseEntity(jobApplication, HttpStatus.CREATED);
		}
		catch (DuplicateApplicationException e) {
			logger.error("Erorr occured while adding new Job application, errorMessage: {}", e.getMessage());
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
	
	
	/**
	 * The PUT API: /application/{applicationId} exposed towards the user interface to allow user to update job application already added.
	 * @param jobApplication The JobApplication object containing all updated details of the new application user wants to add
	 * @param applicationId	The unique ID of the job application sent to User interface while adding the new job application
	 * @param userId  The unique id assigned to user by the authenticator and is sent in request header
	 * @param univId The unique id assigned to University by the authenticator and is sent in request header.
	 * @return ResponseEntity object with response body and appropriate HTTP status code.
	 */
	@PutMapping("/application/{applicationId}")
	public ResponseEntity updateJobApplication(	@Validated @NotNull @NotBlank @RequestBody JobApplication jobApplication,
												@PathVariable @Validated @NotBlank @NotNull long applicationId,
												@RequestHeader("x-uid") @Validated @NotNull String userId, 
												@RequestHeader("x-univ-id") @Validated @NotNull String univId) {
		
		try{
			logger.info("Received request to update Job Application with Id: {} for user id: {}, from school id: {}", jobApplication.getId(), userId, univId);
			
			jobApplication.setUserId(userId);
			jobApplication.setUnivId(univId);
			jobApplication.setId(applicationId);
			
			jobApplicationService.updateExistingApplication(jobApplication);
			
			logger.info("JobApplication with id: {} updated successfully", jobApplication.getId());
			logger.debug("The updated application is: {}",jobApplication.toString());
			return new ResponseEntity(jobApplication, HttpStatus.OK);
		}
		catch(DuplicateApplicationException dex) {
			logger.error("Error while updating job application with id: {}, errorMessage is: {}", jobApplication.getId(), dex.getMessage());
			HashMap<String, Object> errorMap = new HashMap();
			errorMap.put("errorMessage", dex.getMessage());
			errorMap.put("timeStamp", LocalDateTime.now());
			
			return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
		}
		catch (NoJobApplicationException e) {
			logger.error("Error while updating job application with id: {}, errorMessage is: {}", jobApplication.getId(), e.getMessage());
			HashMap<String, Object> errorMap = new HashMap();
			errorMap.put("errorMessage", e.getMessage());
			errorMap.put("timeStamp", LocalDateTime.now());
			
			return new ResponseEntity(errorMap, HttpStatus.NOT_FOUND);
		}
		catch (Exception ex) {
			logger.error("Unexpected error occured while updating job application with id: {}, errorMessage is: {}", jobApplication.getId(), ex.getMessage());

			HashMap<String, Object> errorMap = new HashMap();
			errorMap.put("errorMessage", ex.getMessage());
			errorMap.put("timeStamp", LocalDateTime.now());
			
			return new ResponseEntity(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 * The DELETE API: /application/{id} exposed towards the user interface to allow user delete a job application that was added already
	 * @param id The unique ID assigned to the job application while adding it.
	 * @return ResponseEntity with 201 HTTP status code if success, a 404 status code if id not found, 500 status code otherwise.
	 */
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
	
	
	/**
	 * The GET API: /application exposed towards the user interface to fetch all the job applications added to the system by the user. The API support pagination enabling the user interface fetch records in pages (i.e. few records at a time).
	 * @param startId The unique Id of the job applications starting from which the job applications should be returned  
	 * @param numberOfRecords The maximum number of job applications to be returned.
	 * @param userId  The unique id assigned to user by the authenticator and is sent in request header
	 * @param univId The unique id assigned to University by the authenticator and is sent in request header.
	 * @return Returns a maximum of {numberOfRecords} job applications beginning from the startId.
	 */
	@GetMapping("/application")
	public ResponseEntity fetchJobApplications(	@RequestParam @Min(1) long startId, @RequestParam @Max(2000) long numberOfRecords,
												@RequestHeader("x-uid") @Validated @NotNull String userId, 
												@RequestHeader("x-univ-id") @Validated @NotNull String univId) {
		
		try {
			logger.info("Received request to fetch a maximum of {} Job Applications starting id: {} for user id: {} and school id: {}", numberOfRecords, startId, userId, univId);

			JobApplicationRecords jobApplications =  jobApplicationService.fetchjobApplications(startId, numberOfRecords, userId, univId);
			
			logger.info("Successfully fetched {} Job Applications for user id: {} and school id: {}", jobApplications.getBatchSize(), userId, univId);
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
	
	/**
	 * The GET API: /application/data exposed towards the user interface and allows the UI to fetch statistics of the applications made by the user.
	 * @param userId  The unique id assigned to user by the authenticator and is sent in request header
	 * @param univId The unique id assigned to University by the authenticator and is sent in request header.
	 * @return Returns the meta data of the job applications made by the user.
	 */
	@GetMapping("/application/data")
	public ResponseEntity fetchMetaData(@RequestHeader("x-uid") @Validated @NotNull String userId, 
										@RequestHeader("x-univ-id") @Validated @NotNull String univId) {
		
		try {
			
			logger.info("Received request to fetch job applications meta data for user: {} from university: {}", userId, univId);
			JobApplicationsMetaData jobApplicationsMetaData = jobApplicationService.fetchUserMetaData(userId, univId);
			logger.info("Successfully fetched job applications meta data for user: {} from university: {}", userId, univId);

			return new ResponseEntity(jobApplicationsMetaData, HttpStatus.OK);
			
		}
		catch (Exception e) {
			logger.error("Unexpected error occured while fetching meta data, errorMessage is: {}", e.getMessage());

			HashMap<String, Object> errorMap = new HashMap();
			errorMap.put("errorMessage", e.getMessage());
			errorMap.put("timeStamp", LocalDateTime.now());
			
			return new ResponseEntity(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			}
}
