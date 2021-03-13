package com.safetynet.api.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dao.MedicalRecordDaoImpl;
import com.safetynet.api.dao.PersonDaoImpl;
import com.safetynet.api.exceptions.DataAlreadyExistException;
import com.safetynet.api.exceptions.DataNotFoundException;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;

@Service
public class MedicalRecordService {

	@Autowired
	MedicalRecordDaoImpl medicalRecordDao;

	@Autowired
	PersonDaoImpl personDao;

	public boolean createMedicalRecord(@Valid MedicalRecord medicalRecord) {
		List<Person> personInfo = personDao.findPersonByName(medicalRecord.getFirstName(), medicalRecord.getLastName());
		String message = null;
		if ((!medicalRecordDao.findAll().contains(medicalRecord)) && (personInfo != null) && (!personInfo.isEmpty())) {
			medicalRecordDao.createMedicalRecord(medicalRecord);
			return true;
		} else {
			if (!medicalRecordDao.findAll().contains(medicalRecord)) {
				message = "Le medicalRecord existe";
				return false;
			}
			if ((personInfo != null) || (!personInfo.isEmpty())) {
				message = "La personne " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName()
						+ " n'existe pas.";
				return false;
			}
			throw new DataAlreadyExistException(message);
		}
	}

	public boolean updateMedicalRecord(@Valid MedicalRecord medicalRecord) {
		if (!medicalRecordDao.updateMedicalRecord(medicalRecord)) {
			throw new DataNotFoundException("n'existe pas");
		} else {
			return true;
		}
	}

	public void deleteMedicalRecord(@Valid MedicalRecord medicalRecord) {
		if (!medicalRecordDao.deleteMedicalRecord(medicalRecord)) {
			throw new DataNotFoundException("n'a pas de dossier médical");
		}
	}

}
