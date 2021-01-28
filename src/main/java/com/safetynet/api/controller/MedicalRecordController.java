package com.safetynet.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;

@RestController
public class MedicalRecordController {
	
	@Autowired
	MedicalRecordService medicalRecordService; 
	
	@PostMapping(value="medicalRecord")
	@ResponseStatus(HttpStatus.CREATED)
	public void createMedicalRecord(@RequestBody @Valid MedicalRecord medicalRecord) {
		 medicalRecordService.createMedicalRecord(medicalRecord);
	}
	
	@PutMapping(value="medicalRecord")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateMedicalRecord(@RequestBody @Valid MedicalRecord medicalRecord) {
		 medicalRecordService.updateMedicalRecord(medicalRecord);
	}
	
	@DeleteMapping (value="medicalRecord")
	@ResponseStatus(HttpStatus.RESET_CONTENT)
	public void deleteMedicalRecord(@RequestBody @Valid MedicalRecord medicalRecord) {
		 medicalRecordService.deleteMedicalRecord(medicalRecord);
		 
	}
}
