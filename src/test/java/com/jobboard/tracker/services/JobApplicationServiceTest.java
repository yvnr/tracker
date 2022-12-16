package com.jobboard.tracker.services;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jobboard.tracker.enums.JobStatusEnum;
import com.jobboard.tracker.exceptions.DuplicateApplicationException;
import com.jobboard.tracker.exceptions.NoJobApplicationException;
import com.jobboard.tracker.mappers.JobApplicationMapper;
import com.jobboard.tracker.models.JobApplication;
import com.jobboard.tracker.models.JobApplicationAsEntity;
import com.jobboard.tracker.models.JobApplicationRecords;
import com.jobboard.tracker.models.JobApplicationsMetaData;
import com.jobboard.tracker.validation.ValidationService;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class JobApplicationServiceTest {

	@Mock
	ValidationService validationService;
	
	@Mock
	JobApplicationMapper jobApplicationMapper;
	
	@InjectMocks
	JobApplicationService jobApplicationService;
	
	@Test
	void testPersistJobApplication() {
		JobApplication jobApplication = new JobApplication();
		jobApplication.setCompany("Amazon");
		jobApplication.setId(1L);
		jobApplication.setJobId("jobamxn123");
		jobApplication.setLocation("SFO");
		jobApplication.setPosition("SDE1");
		jobApplication.setStatus(JobStatusEnum.APPLIED);
		jobApplication.setTime(new Date(System.currentTimeMillis()));
		jobApplication.setUnivId("university");
		jobApplication.setUserId("user");

		when(validationService.checkIfApplicationExist(any(JobApplication.class))).thenReturn(true);
		assertThrows(DuplicateApplicationException.class, () -> jobApplicationService.persistJobApplication(jobApplication));
		
		when(validationService.checkIfApplicationExist(any(JobApplication.class))).thenReturn(false);
		when(jobApplicationMapper.addNewApplication(any(JobApplicationAsEntity.class))).thenReturn(1L);
		
		jobApplicationService.persistJobApplication(jobApplication);

	}

	@Test
	void testUpdateExistingApplication() {
		JobApplication jobApplication = new JobApplication();
		jobApplication.setCompany("Amazon");
		jobApplication.setId(1L);
		jobApplication.setJobId("jobamxn123");
		jobApplication.setLocation("SFO");
		jobApplication.setPosition("SDE1");
		jobApplication.setStatus(JobStatusEnum.APPLIED);
		jobApplication.setTime(new Date(System.currentTimeMillis()));
		jobApplication.setUnivId("university");
		jobApplication.setUserId("user");
		
		JobApplicationAsEntity jobApplicationAsEntity = new JobApplicationAsEntity();
		jobApplicationAsEntity.setStatus(JobStatusEnum.APPLIED.toString());when(validationService.validateId(1L)).thenReturn(jobApplicationAsEntity);
		when(validationService.checkIfApplicationExist(any(JobApplicationAsEntity.class))).thenReturn(true);
		assertThrows(DuplicateApplicationException.class, () -> jobApplicationService.updateExistingApplication(jobApplication));
		

		when(validationService.validateId(1L)).thenThrow(NoJobApplicationException.class);
		assertThrows(NoJobApplicationException.class, () -> jobApplicationService.updateExistingApplication(jobApplication));

	}

	@Test
	void testDeleteJobApplication() {
		when(validationService.validateId(1L)).thenReturn(null);
		doNothing().when(jobApplicationMapper).deleteJobApplicationWithId(1L);
		jobApplicationService.deleteJobApplication(1L);
		assertTrue(true);
	}

	
	@Test
	void testFetchjobApplications() {
		JobApplicationAsEntity jobApplicationAsEntity = new JobApplicationAsEntity();
		jobApplicationAsEntity.setCompany("Amzn");
		jobApplicationAsEntity.setId(1L);
		jobApplicationAsEntity.setJobId("jobamzn123");
		jobApplicationAsEntity.setLocation("SFO");
		jobApplicationAsEntity.setPosition("SDE1");
		jobApplicationAsEntity.setStatus("APPLIED");
		jobApplicationAsEntity.setUnivId("univ");
		jobApplicationAsEntity.setUserId("userId");
		jobApplicationAsEntity.setAppliedTime(new Date(System.currentTimeMillis()));
		
		ArrayList<JobApplicationAsEntity> list = new ArrayList<JobApplicationAsEntity>();
		list.add(jobApplicationAsEntity);
		
		when(jobApplicationMapper.fetchJobApplications(any(Long.class), any(Long.class), any(String.class), any(String.class))).thenReturn(list);
		when(jobApplicationMapper.getBeginId(any(String.class), any(String.class))).thenReturn(1L);
		when(jobApplicationMapper.getEndId(any(String.class), any(String.class))).thenReturn(1L);
		
		JobApplicationRecords records = jobApplicationService.fetchjobApplications(1L, 5, "user", "univ");
		assertEquals(1, records.getBatchSize());
		
		when(jobApplicationMapper.fetchJobApplications(any(Long.class), any(Long.class), any(String.class), any(String.class))).thenReturn(new ArrayList());
		records = jobApplicationService.fetchjobApplications(1L, 5, "user", "univ");
		assertEquals(0, records.getBatchSize());
		
	}

	@Test
	void testFetchUserMetaData() {
		when(jobApplicationMapper.fetchInprogressCountByUserId(any(String.class), any(String.class))).thenReturn(2L);
		when(jobApplicationMapper.fetchJobApplicationsCount(any(String.class), any(String.class))).thenReturn(5L);
		when(jobApplicationMapper.fetchJobOffersCountByUserId(any(String.class), any(String.class))).thenReturn(1L);
		JobApplicationsMetaData metaData = jobApplicationService.fetchUserMetaData("usre", "univ");
		assertEquals(1L, metaData.getOffersCount());
		assertEquals(2L, metaData.getInProgressCount());
		assertEquals(5L, metaData.getTotalApplicationsCount());

	}

}
