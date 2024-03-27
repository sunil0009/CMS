package com.example.cms.exception;

public class UserNotFoundException extends RuntimeException {
	private String message;

	public UserNotFoundException(String string) {
		this.message=message;	}

	public String getMessage() {
		return message;
	}

}
