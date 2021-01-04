package com.safetynet.api.dao;

import java.util.List;

import com.safetynet.api.dto.FamilyMembers;
import com.safetynet.api.model.Person;

public interface IPersonDao  {
	
	public List<Person>findAll();
	public List<String>findPersonsNumberPhonesServedByFirestation(List<String> addressFirestation);
	public List<Person>findPersonByName(String firstName, String lastName);
	public List<Person>findPersonsServedByFirestationAddress(List<String> addressFirestation);
	public List<Person>findPersonsByAddress(String address);
	public boolean findMinorInList(List<FamilyMembers> familyMembers);
}
