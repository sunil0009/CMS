package com.example.cms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAlreadyExistByEmailException extends RuntimeException {

	private String message;

	public UserAlreadyExistByEmailException(String string) {
		this.message=message;	}

	public String getMessage() {
		return message;
	}



}
