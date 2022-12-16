package com.jobboard.tracker.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.jobboard.tracker.exceptions.DuplicateApplicationException;
import com.jobboard.tracker.exceptions.NoJobApplicationException;
import com.jobboard.tracker.models.JobApplication;
import com.jobboard.tracker.models.JobApplicationRecords;
import com.jobboard.tracker.services.JobApplicationService;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class JobApplicationControllerTest {

	@Mock
	JobApplicationService jobApplicationService;
	
	@InjectMocks
	JobApplicationController jobApplicationController;
	
	@Test
	void testAddNewJobApplication() {
		doNothing().when(jobApplicationService).persistJobApplication(any(JobApplication.class));
		ResponseEntity result = jobApplicationController.addNewJobApplication(new JobApplication(), "user", "univ");
		assertEquals(201, result.getStatusCodeValue());
		
		doThrow(RuntimeException.class).when(jobApplicationService).persistJobApplication(any(JobApplication.class));
		result = jobApplicationController.addNewJobApplication(new JobApplication(), "user", "univ");
		assertEquals(500, result.getStatusCodeValue());
		
		doThrow(DuplicateApplicationException.class).when(jobApplicationService).persistJobApplication(any(JobApplication.class));
		result = jobApplicationController.addNewJobApplication(new JobApplication(), "user", "univ");
		assertEquals(400, result.getStatusCodeValue());
	}

	@Test
	void testUpdateJobApplication() {
		when(jobApplicationService.updateExistingApplication(any(JobApplication.class))).thenReturn(null);
		ResponseEntity result = jobApplicationController.updateJobApplication(new JobApplication(), 1, "user", "univ");
		assertEquals(200, result.getStatusCodeValue());
		
		when(jobApplicationService.updateExistingApplication(any(JobApplication.class))).thenThrow(DuplicateApplicationException.class);
		result = jobApplicationController.updateJobApplication(new JobApplication(), 1, "user", "univ");
		assertEquals(400, result.getStatusCodeValue());
		
		when(jobApplicationService.updateExistingApplication(any(JobApplication.class))).thenThrow(NoJobApplicationException.class);
		result = jobApplicationController.updateJobApplication(new JobApplication(), 1, "user", "univ");
		assertEquals(404, result.getStatusCodeValue());
		
		when(jobApplicationService.updateExistingApplication(any(JobApplication.class))).thenThrow(RuntimeException.class);
		result = jobApplicationController.updateJobApplication(new JobApplication(), 1, "user", "univ");
		assertEquals(500, result.getStatusCodeValue());
	}

	@Test
	void testRemoveJobApplication() {
		doNothing().when(jobApplicationService).deleteJobApplication(any(Long.class));
		ResponseEntity result = jobApplicationController.removeJobApplication(2L);
		assertEquals(200, result.getStatusCodeValue());
		
		doThrow(NoJobApplicationException.class).when(jobApplicationService).deleteJobApplication(any(Long.class));
		result = jobApplicationController.removeJobApplication(2L);
		assertEquals(404, result.getStatusCodeValue());
		
		doThrow(RuntimeException.class).when(jobApplicationService).deleteJobApplication(any(Long.class));
		result = jobApplicationController.removeJobApplication(2L);
		assertEquals(500, result.getStatusCodeValue());
	}

	@Test
	void testFetchJobApplications() {
		
		when(jobApplicationService.fetchjobApplications(any(Long.class), any(Long.class), any(String.class), any(String.class))).thenReturn(new JobApplicationRecords());
		ResponseEntity result = jobApplicationController.fetchJobApplications(2L, 100L, "user", "univ");
		assertEquals(200, result.getStatusCodeValue());

		when(jobApplicationService.fetchjobApplications(any(Long.class), any(Long.class), any(String.class), any(String.class))).thenThrow(RuntimeException.class);
		result = jobApplicationController.fetchJobApplications(2L, 100L, "user", "univ");
		assertEquals(500, result.getStatusCodeValue());
	}

	@Test
	void testFetchMetaData() {
		when(jobApplicationService.fetchUserMetaData(any(String.class), any(String.class))).thenReturn(null);
		
		ResponseEntity result = jobApplicationController.fetchMetaData("user", "univ");
		assertEquals(200, result.getStatusCodeValue());

		when(jobApplicationService.fetchUserMetaData(any(String.class), any(String.class))).thenThrow(RuntimeException.class);
		
		result = jobApplicationController.fetchMetaData("user", "univ");
		assertEquals(500, result.getStatusCodeValue());	}

}
