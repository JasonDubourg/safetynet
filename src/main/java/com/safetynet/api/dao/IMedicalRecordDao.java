package com.safetynet.api.dao;

import java.util.List;

import com.safetynet.api.model.MedicalRecord;

public interface IMedicalRecordDao {

	public List<MedicalRecord>findAll();
	public List<String>findAllergiesByName(String firstName, String lastName); 
	public List<String>findMedicationsByName(String firstName, String lastName);
	public String findBirthdateByName(String firstName, String lastName);
}
