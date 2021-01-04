package com.safetynet.api.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.api.model.MedicalRecord;

@Repository
public class MedicalRecordDaoImpl implements IMedicalRecordDao {
	
	@Autowired
	private DataRepository dataRepository; 

	@Override
	public List<MedicalRecord> findAll() {
		return dataRepository.database.getMedicalrecords();
	}

	@Override
	public List<String> findAllergiesByName(String firstName, String lastName) {
		List<String>allergies = new ArrayList<String>(); 
		List<MedicalRecord> medicalRecordList = dataRepository.database.getMedicalrecords();
		for (MedicalRecord medicalRecord : medicalRecordList) {
				if((firstName == null || medicalRecord.getFirstName().equalsIgnoreCase(firstName)) && (lastName == null || medicalRecord.getLastName().equalsIgnoreCase(lastName))) { 
					allergies.addAll(medicalRecord.getAllergies());
			}
		}
		return allergies;  
	}
	
	@Override
	public List<String> findMedicationsByName(String firstName, String lastName) {
		List<String>medications = new ArrayList<String>(); 
		List<MedicalRecord> medicalRecordList = dataRepository.database.getMedicalrecords();
		for (MedicalRecord medicalRecord : medicalRecordList) {
				if((firstName == null || medicalRecord.getFirstName().equalsIgnoreCase(firstName)) && (lastName == null || medicalRecord.getLastName().equalsIgnoreCase(lastName))) { 
					medications.addAll(medicalRecord.getMedications());
			}
		}
		return medications;  
	}
	
	@Override
	public String findBirthdateByName(String firstName, String lastName) {
		String birthdate = "";
		List<MedicalRecord> medicalRecordList = dataRepository.database.getMedicalrecords();
		for (MedicalRecord medicalRecord : medicalRecordList) {
				if((firstName == null || medicalRecord.getFirstName().equalsIgnoreCase(firstName)) && (lastName == null || medicalRecord.getLastName().equalsIgnoreCase(lastName))) { 
					birthdate = medicalRecord.getBirthdate();
			}
		}
		return birthdate;  
	}

}
