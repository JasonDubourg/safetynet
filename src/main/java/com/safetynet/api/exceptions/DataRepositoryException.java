package com.safetynet.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataRepositoryException extends RuntimeException {

	private static final long serialVersionUID = 3076907416263732725L;

	public DataRepositoryException(String message, Throwable cause) {

		super(message, cause);
	}

}

