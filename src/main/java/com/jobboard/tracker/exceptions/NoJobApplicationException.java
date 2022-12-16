package com.jobboard.tracker.exceptions;

/**
 * A custom exception thrown when a job application is not found in the database
 * @author Nani
 *
 */
public class NoJobApplicationException extends RuntimeException{

	public NoJobApplicationException(String message) {
		super(message);
	}
	
}
