package com.safetynet.api.dto;

import java.util.List;

public class FirestationCoveragePersonWithCounting {

	private List <FirestationCoveragePerson> firestationCoveragePerson; 
	private int cptAdult; 
	private int cptChild;
	
	public List<FirestationCoveragePerson> getFireStationCoveragePerson() {
		return firestationCoveragePerson;
	}
	public void setFirestationCoveragePerson(List<FirestationCoveragePerson> firestationCoveragePerson) {
		this.firestationCoveragePerson = firestationCoveragePerson;
	}
	public int getCptAdult() {
		return cptAdult;
	}
	public void setCptAdult(int cptAdult) {
		this.cptAdult = cptAdult;
	}
	public int getCptChild() {
		return cptChild;
	}
	public void setCptChild(int cptChild) {
		this.cptChild = cptChild;
	}

}
