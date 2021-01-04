package com.safetynet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.service.MedicalRecordService;

@RestController
public class MedicalRecordController {
	
	@Autowired
	MedicalRecordService medicalRecordService; 
	
	@GetMapping(value="/MedicalRecord")
	@ResponseStatus(HttpStatus.OK)
	public String person() {
		return "Jason n'a pas d'allergies"; 
	}
	
	@PostMapping(value="/MedicalRecordAdd")
	public String postPerson() {
		return "Jason"; 
	}
	
	@PutMapping(value="/MedicalRecordUpdate")
	public String updatePerson() {
		return "Jason"; 
	}
	
	@DeleteMapping (value="/MedicalRecordDelete")
	public String deletePerson() {
		return "Jason"; 
	}
}
