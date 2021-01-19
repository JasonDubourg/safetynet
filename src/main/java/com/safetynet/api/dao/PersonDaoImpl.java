package com.safetynet.api.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.api.dto.FamilyMembers;
import com.safetynet.api.model.Person;

@Repository
public class PersonDaoImpl implements IPersonDao {
	
	@Autowired
	DataRepository dataRepository;

	@Override
	public boolean createPerson(Person person) {
		//Ajout de la nouvelle personne en mémoire
		dataRepository.database.getPersons().add(person);
		//Commit les changements pour les appliquer sur le fichier json => écriture
		dataRepository.commit();
		return true;
	}

	@Override
	public boolean updatePerson(Person person) {
		if(dataRepository.database.getPersons().remove(person)) {
			this.createPerson(person);
			return true;
		}
		return false;
	}

	@Override
	public boolean deletePerson(Person person) {
		boolean result = dataRepository.database.getPersons().remove(person);
		dataRepository.commit();
		return result;
	}
	
	@Override
	public List<Person> findAll() {
		 return dataRepository.database.getPersons();
	}

	@Override
	public List<String> findPersonsNumberPhonesServedByFirestation(List<String> addressFirestation) {	
		List <String> numberPhones = new ArrayList<String>(); 
		List<Person> persons = dataRepository.database.getPersons();
		for (String address : addressFirestation) {
			for (Person person : persons) {
				if(address.equals(person.getAddress())) {
					numberPhones.add(person.getPhone()); 
				}
			}
		}
		return numberPhones;
	}

	@Override
	public List<Person> findPersonByName(String firstName, String lastName) {
			List <Person> personList = new ArrayList<>(); 
			List<Person> persons = dataRepository.database.getPersons();
			for (Person person : persons) {
				if((firstName == null || person.getFirstName().equalsIgnoreCase(firstName)) && (lastName == null || person.getLastName().equalsIgnoreCase(lastName))) {          
					 personList.add(person);
				}
			}
			return personList; 
	}
	
	@Override
	public List<Person> findPersonsServedByFirestationAddress(List<String> addressFirestation) {	
		List <Person> personByAddressList = new ArrayList<Person>(); 
		List<Person> persons = dataRepository.database.getPersons();
		for (String address : addressFirestation) {
			for (Person person : persons) {
				if(address.equals(person.getAddress())) {
					personByAddressList.add(person); 
				}
			}
		}
		return personByAddressList;
	}

	@Override
	public List<Person> findPersonsByAddress(String address) {
		List <Person> personsByAddressList = new ArrayList<Person>(); 
		List<Person> persons = dataRepository.database.getPersons();
		for (Person person : persons) {
			if(person.getAddress().equalsIgnoreCase(address)) {
				personsByAddressList.add(person); 
			}
		}
		return personsByAddressList;
	}
	
	@Override
	public boolean findMinorInList(List<FamilyMembers> familyMembers){
		boolean isMinor = false; 
		for (FamilyMembers member : familyMembers) {
			if(member.isMinor()) {
				isMinor = true; 
			}
		}
		return isMinor;
	}



	
	
	
}
