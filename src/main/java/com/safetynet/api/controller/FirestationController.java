package com.safetynet.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.dto.FirestationCoveragePersonWithCounting;
import com.safetynet.api.dto.FirestationPersonAtAddress;
import com.safetynet.api.dto.PersonsInfoWithFirestation;
import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.FirestationService;

@RestController
public class FirestationController {
	
	@Autowired
	FirestationService firestationService; 
	
	@Autowired
	private FirestationService firestationSerivce; 

	@GetMapping(value="/phoneAlert")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getPersonsPhoneNumbersByFirestationNumber(@RequestParam("firestation") String station) {
		return firestationSerivce.getPersonsPhoneNumbersByFirestationNumber(station); 
	}
	
	@GetMapping(value="/firestation")
	@ResponseStatus(HttpStatus.OK)
	public FirestationCoveragePersonWithCounting getListFirestationCoveragePerson(@RequestParam("stationNumber") String station) {
		return firestationSerivce.getFirestationWithPersonInfo(station); 
	}
	
	@GetMapping(value="/fire")
	@ResponseStatus(HttpStatus.OK)
	public PersonsInfoWithFirestation getListPersonsAndNumberFirestationByAddress(@RequestParam("address") String address) {
		return firestationSerivce.getListPersonsAndNumberFirestationByAddress(address); 
	}
	
	@GetMapping(value="/flood/stations")
	@ResponseStatus(HttpStatus.OK)
	public List <FirestationPersonAtAddress> getListHomesByStation(@RequestParam("stationNumber") List <String> stations) {
		return firestationSerivce.getFirestationCoveragePerson(stations); 
	}
	
	@PostMapping(value="firestation")
	@ResponseStatus(HttpStatus.CREATED)
	public void createFirestation(@RequestBody @Valid Firestation firestation) {
		firestationSerivce.createFirestation(firestation);
	}
	
	@PutMapping(value="firestation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateFirestation(@RequestBody @Valid Firestation firestation) {
		firestationSerivce.updateFirestation(firestation);
	}
	
	@DeleteMapping (value="firestation")
	@ResponseStatus(HttpStatus.RESET_CONTENT)
	public void deleteFirestation(@RequestBody @Valid Firestation firestation) {
		firestationSerivce.deleteFirestation(firestation);
	}
}
