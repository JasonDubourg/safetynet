package com.safetynet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.dto.ChildAndParentsByAddress;
import com.safetynet.api.dto.PersonInfo;
import com.safetynet.api.service.MedicalRecordService;
import com.safetynet.api.service.PersonService;

@RestController
public class PersonController {
	
	@Autowired
	PersonService personService; 
	
	@Autowired
	MedicalRecordService medicalRecordService; 
	
	@GetMapping(value="/childAlert")
	@ResponseStatus(HttpStatus.OK)
	public ChildAndParentsByAddress getChildAndFamilyByAddress(@RequestParam("address") String address) {
		return personService.getChildAndFamilyMembersByAddress(address); 
	}
	
	@GetMapping(value="/personInfo")
	@ResponseStatus(HttpStatus.OK)
	public List <PersonInfo> getPersonInfo(@RequestParam(required = false) String firstName, @RequestParam("lastName") String lastName ) {
		return personService.getPersonInfo(firstName, lastName); 
	}
	
	@GetMapping(value="/communityEmail")
	@ResponseStatus(HttpStatus.OK)
	public List <String> getPersonEmailByCity(@RequestParam("city") String city) {
		return personService.getPersonEmailByCity(city); 
	}
	
	@PostMapping(value="/Person")
	public String postPerson() {
		return "Jason"; 
	}
	
	@PutMapping(value="/Persons/{id}")
	public String updatePerson() {
		return "Jason"; 
	}
	
	@DeleteMapping (value="/Persons/{id}")
	public String deletePerson() {
		return "Jason"; 
	}

}
