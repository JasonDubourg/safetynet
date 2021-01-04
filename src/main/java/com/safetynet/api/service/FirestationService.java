package com.safetynet.api.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dao.FirestationDaoImpl;
import com.safetynet.api.dao.MedicalRecordDaoImpl;
import com.safetynet.api.dao.PersonDaoImpl;
import com.safetynet.api.dto.FirestationCoveragePerson;
import com.safetynet.api.dto.FirestationCoveragePersonWithCounting;
import com.safetynet.api.dto.FirestationNumber;
import com.safetynet.api.dto.PersonInfoByAddress;
import com.safetynet.api.dto.PersonsInfoWithFirestation;
import com.safetynet.api.model.Person;
import com.safetynet.api.util.CalculAge;
import com.safetynet.api.util.DefineMajority;

@Service
public class FirestationService {
	
	@Autowired
	private FirestationDaoImpl firestationDaoImpl; 
	
	@Autowired
	private PersonDaoImpl personDaoImpl; 
	
	@Autowired
	private MedicalRecordDaoImpl medicalRecordDaoImpl;
	
	public List <String> getPersonsPhoneNumbersByFirestationNumber(String station) {
		List <String> firestationsAddress = firestationDaoImpl.findFirestationByNumberStation(station);
		return personDaoImpl.findPersonsNumberPhonesServedByFirestation(firestationsAddress); 
	}
	
	public FirestationCoveragePersonWithCounting getFirestationCoveragePerson(String station){
		List <String> firestationsAddress = firestationDaoImpl.findFirestationByNumberStation(station);
		List <FirestationCoveragePerson> personsList = new ArrayList<>(); 
		FirestationCoveragePersonWithCounting firestationCoveragePersonWithCounting = new FirestationCoveragePersonWithCounting();
		List <Person> personAddressList = personDaoImpl.findPersonsServedByFirestationAddress(firestationsAddress); 
		for (Person personAddress : personAddressList) {
			FirestationCoveragePerson firestationCoveragePerson = new FirestationCoveragePerson(); 
			firestationCoveragePerson.setFirstName(personAddress.getFirstName());
			firestationCoveragePerson.setLastName(personAddress.getLastName());
			firestationCoveragePerson.setAddress(personAddress.getAddress());
			firestationCoveragePerson.setPhone(personAddress.getPhone());	
			personsList.add(firestationCoveragePerson);
		}
		firestationCoveragePersonWithCounting.setFirestationCoveragePerson(personsList);
		for (FirestationCoveragePerson person : firestationCoveragePersonWithCounting.getFireStationCoveragePerson()) {
			if(DefineMajority.isMinor(CalculAge.calculAgeWithBirthdate(medicalRecordDaoImpl.findBirthdateByName(person.getFirstName(), person.getLastName())))) {
				firestationCoveragePersonWithCounting.setCptChild(firestationCoveragePersonWithCounting.getCptChild() + 1);
			} else {
				firestationCoveragePersonWithCounting.setCptAdult(firestationCoveragePersonWithCounting.getCptAdult() + 1);
			}
		}
		return firestationCoveragePersonWithCounting;
	}
	
	public PersonsInfoWithFirestation getListPersonsAndNumberFirestationByAddress(String address) {
		List<Person> personsAddress = personDaoImpl.findPersonsByAddress(address);
		PersonsInfoWithFirestation personsInfoWithFirestation = new PersonsInfoWithFirestation();
		List<PersonInfoByAddress> personsList = new ArrayList<>();
		List<FirestationNumber> station = new ArrayList<>();
		for (Person person : personsAddress) {
			PersonInfoByAddress personInfo = new PersonInfoByAddress(); 
			personInfo.setLastName(person.getLastName());
			personInfo.setPhone(person.getPhone());
			personInfo.setAge(CalculAge.calculAgeWithBirthdate(medicalRecordDaoImpl.findBirthdateByName(person.getFirstName(), person.getLastName())));
			personInfo.setMedications(medicalRecordDaoImpl.findMedicationsByName(person.getFirstName(), person.getLastName()));
			personInfo.setAllergies(medicalRecordDaoImpl.findAllergiesByName(person.getFirstName(), person.getLastName()));
			personsList.add(personInfo);

		}
		FirestationNumber firestation = new FirestationNumber();
		firestation.setStation(firestationDaoImpl.findFirestationNumberbyAddress(address));
		station.add(firestation);
		personsInfoWithFirestation.setPersonInfoByAddress(personsList);
		personsInfoWithFirestation.setFirestationNumber(station);
		return personsInfoWithFirestation;
	}
}
