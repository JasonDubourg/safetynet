package com.safetynet.api.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataAlreadyExistException extends RuntimeException {
	
	private static final long serialVersionUID = -996509561697972212L;

	public DataAlreadyExistException(String message) {

		super(message);

	}

}

