package com.jobboard.tracker.exceptions;

public class DuplicateApplicationException extends RuntimeException{

	public DuplicateApplicationException(String message) {
		super(message);
	}
}
