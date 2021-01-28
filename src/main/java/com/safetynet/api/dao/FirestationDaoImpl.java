package com.safetynet.api.dao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.api.model.Firestation;

@Repository
public class FirestationDaoImpl implements IFirestationDao {
	
	@Autowired
	DataRepository dataRepository; 

	@Override
	public List<Firestation> findAll() {
		return dataRepository.database.getFirestations();
	}

	@Override
	public List<String> findFirestationByNumberStation(String station) {	
		List<String> firestationsAddress = new ArrayList<String>(); 
		List<Firestation> firestations = dataRepository.database.getFirestations();
		for (Firestation firestation : firestations) {
			if(station.equals(firestation.getStation())) {
				firestationsAddress.add(firestation.getAddress()); 
			}
		} 
		return firestationsAddress; 
	}
	
	@Override
	public List<Firestation> findFirestationByStation(String station) {	
		List<Firestation> firestations = new ArrayList<>(); 
		List<Firestation> firestationsRepo = dataRepository.database.getFirestations();
		for (Firestation firestation : firestationsRepo) {
			if(station.equals(firestation.getStation())) {
				firestations.add(firestation); 
			}
		} 
		return firestations; 
	}

	@Override
	public List<String> findFirestationNumberbyAddress(String address) {
		List <String> stationNumber = new ArrayList<String>();
		List<Firestation> firestations = dataRepository.database.getFirestations();
		for (Firestation firestation : firestations) {
			if(address.equalsIgnoreCase(firestation.getAddress())) {
				stationNumber.add(firestation.getStation());
			}
		}
		return stationNumber;
	}

	public boolean createFirestation(@Valid Firestation firestation) {
		boolean result = dataRepository.database.getFirestations().add(firestation);
		dataRepository.commit();
		return result;
	}
	
	//delete
	
	//update
	

}
