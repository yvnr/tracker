package com.jobboard.tracker.validation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jobboard.tracker.exceptions.NoJobApplicationException;
import com.jobboard.tracker.mappers.JobApplicationMapper;
import com.jobboard.tracker.models.JobApplication;
import com.jobboard.tracker.models.JobApplicationAsEntity;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ValidationServiceTest {

	@Mock
	JobApplicationMapper jobApplicationMapper;
	
	@InjectMocks
	ValidationService validationService;
	
	@Test
	void testValidateId() {
		JobApplicationAsEntity jobEntity = new JobApplicationAsEntity();
		when(jobApplicationMapper.findApplicationById(1L)).thenReturn(jobEntity);
		assertEquals(jobEntity, validationService.validateId(1L));
		
		when(jobApplicationMapper.findApplicationById(1L)).thenReturn(null);
		assertThrows(NoJobApplicationException.class, () -> validationService.validateId(1L));
		
	}

	@Test
	void testCheckIfApplicationExistJobApplication() {
		JobApplication jobApplication = new JobApplication();
		when(jobApplicationMapper.findApplicationByUniqueIdentifiers(jobApplication)).thenReturn(2L);
		assertTrue(validationService.checkIfApplicationExist(jobApplication));
		
		when(jobApplicationMapper.findApplicationByUniqueIdentifiers(jobApplication)).thenReturn(0L);
		assertFalse(validationService.checkIfApplicationExist(jobApplication));

	}

	@Test
	void testCheckIfApplicationExistJobApplicationAsEntity() {
		JobApplicationAsEntity jobApplication = new JobApplicationAsEntity();
		when(jobApplicationMapper.findApplicationByUniqueIdentifiersWithEntity(jobApplication)).thenReturn(2);
		assertTrue(validationService.checkIfApplicationExist(jobApplication));
		
		when(jobApplicationMapper.findApplicationByUniqueIdentifiersWithEntity(jobApplication)).thenReturn(0);
		assertFalse(validationService.checkIfApplicationExist(jobApplication));

	}

}
