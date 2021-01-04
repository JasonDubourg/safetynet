package com.safetynet.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dao.MedicalRecordDaoImpl;
import com.safetynet.api.dao.PersonDaoImpl;
import com.safetynet.api.dto.ChildAndParentsByAddress;
import com.safetynet.api.dto.ChildByAddress;
import com.safetynet.api.dto.FamilyMembers;
import com.safetynet.api.dto.ParentByAddress;
import com.safetynet.api.dto.PersonInfo;
import com.safetynet.api.model.Person;
import com.safetynet.api.util.CalculAge;
import com.safetynet.api.util.DefineMajority;

@Service
public class PersonService {

	@Autowired
	private PersonDaoImpl personDaoImpl; 
	
	@Autowired
	private MedicalRecordDaoImpl medicalRecordDaoImpl; 
	
	// Récupère les emails des personnes en fonction du nom de la ville
	public List <String> getPersonEmailByCity(String city) {
		List <Person> persons = personDaoImpl.findAll();
		return persons.stream()
								.filter(person-> person.getCity().equalsIgnoreCase(city))
								.map(person -> person.getEmail())
								.collect(Collectors.toList());
	}
	
	// Récupère les informations d'une personne en fonction de son prénom et nom
	public List <PersonInfo> getPersonInfo(String firstName, String lastName){
		List<PersonInfo> personInfoList = new ArrayList<PersonInfo>();
		List<Person> personList = personDaoImpl.findPersonByName(firstName, lastName); 
		for (Person person : personList) {
			PersonInfo personInfo = new PersonInfo(); 
			personInfo.setFirstName(person.getFirstName());
			personInfo.setLastName(person.getLastName());
			personInfo.setAddress(person.getAddress());
			personInfo.setAge(CalculAge.calculAgeWithBirthdate(medicalRecordDaoImpl.findBirthdateByName(person.getFirstName(), person.getLastName())));
			personInfo.setEmail(person.getEmail()); 
			personInfo.setAllergies(medicalRecordDaoImpl.findAllergiesByName(person.getFirstName(), person.getLastName())); 
			personInfo.setMedications(medicalRecordDaoImpl.findMedicationsByName(person.getFirstName(), person.getLastName()));
			personInfoList.add(personInfo); 
		}
		return personInfoList; 
	}
	
	public ChildAndParentsByAddress getChildAndFamilyMembersByAddress(String address) {
		List<Person> personsByAddress = personDaoImpl.findPersonsByAddress(address); 
		List<FamilyMembers> familyMembers = new ArrayList<>(); 
		List<ChildByAddress> childByAddress = new ArrayList<>();
		List<ParentByAddress> childParents = new ArrayList<>(); 
		ChildAndParentsByAddress childAndParentsByAddress = new ChildAndParentsByAddress();
		// Récupère tous les membres de la famille à l'addresse indiquée.
		for(Person person : personsByAddress) {
			FamilyMembers familyMember = new FamilyMembers(); 
			familyMember.setFirstName(person.getFirstName());
			familyMember.setLastName(person.getLastName());
			familyMember.setAge(CalculAge.calculAgeWithBirthdate(medicalRecordDaoImpl.findBirthdateByName(person.getFirstName(), person.getLastName())));
			familyMember.setMinor(DefineMajority.isMinor(familyMember.getAge()));
			familyMembers.add(familyMember);
		}
		// Vérifie si il y a un mineur dans la famille.
		if(personDaoImpl.findMinorInList(familyMembers)) {
			// Si oui, création de la liste des enfants.
			for(FamilyMembers member : familyMembers) {
				// Si mineur on remplit ses informations.
				ChildByAddress child = new ChildByAddress();
				if(member.isMinor()) {
					child.setFirstName(member.getFirstName());
					child.setLastName(member.getLastName());
					child.setAge(member.getAge());
					childByAddress.add(child);
				} else {
					// Sinon ajouté à la liste des ses parents.
					ParentByAddress parent = new ParentByAddress();
					parent.setFirstName(member.getFirstName());
					parent.setLastName(member.getLastName());
					childParents.add(parent);
				}
			}
		}
		// Ajout des deux listes enfant et parent 
		childAndParentsByAddress.setChildByAddress(childByAddress);
		childAndParentsByAddress.setParentByAddress(childParents);
		return childAndParentsByAddress;
	}
}
