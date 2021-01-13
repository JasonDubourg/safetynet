package com.safetynet.api.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7706466410397192089L;

	public DataNotFoundException(String message) {

		super(message);

	}

}

