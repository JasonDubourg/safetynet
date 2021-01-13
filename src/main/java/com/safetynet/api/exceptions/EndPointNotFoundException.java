package com.safetynet.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EndPointNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 818780479463842528L;

	public EndPointNotFoundException(String s) {
		super(s);
	}

}