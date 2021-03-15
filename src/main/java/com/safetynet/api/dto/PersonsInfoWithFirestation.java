package com.safetynet.api.dto;

import java.util.ArrayList;
import java.util.List;

public class PersonsInfoWithFirestation {

	public List<PersonInfoByAddress> personInfoByAddress = new ArrayList<>();
	public List<FirestationNumber> firestationNumber;

	public List<PersonInfoByAddress> getPersonInfoByAddress() {
		return personInfoByAddress;
	}

	public void setPersonInfoByAddress(List<PersonInfoByAddress> personInfoByAddress) {
		this.personInfoByAddress = personInfoByAddress;
	}

	public List<FirestationNumber> getFirestationNumber() {
		return firestationNumber;
	}

	public void setFirestationNumber(List<FirestationNumber> firestationNumber) {
		this.firestationNumber = firestationNumber;
	}

	@Override
	public String toString() {
		return "PersonsInfoWithFirestation [personInfoByAddress=" + personInfoByAddress + ", firestationNumber="
				+ firestationNumber + "]";
	}

}
