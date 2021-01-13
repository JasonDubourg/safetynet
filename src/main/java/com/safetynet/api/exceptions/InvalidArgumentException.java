package com.safetynet.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends RuntimeException {

	private static final long serialVersionUID = 2236866090835772953L;

	public InvalidArgumentException(String message) {

		super(message);

	}

}
