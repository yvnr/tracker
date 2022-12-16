package com.jobboard.tracker.exceptions;
/**
 * A custom exception thrown when a job application already exists in the database
 */
public class DuplicateApplicationException extends RuntimeException{

	public DuplicateApplicationException(String message) {
		super(message);
	}
}
