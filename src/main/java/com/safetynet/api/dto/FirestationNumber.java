package com.safetynet.api.dto;

import java.util.List;

public class FirestationNumber {

	public List<String> station;

	public List<String> getStation() {
		return station;
	}

	public void setStation(List<String> station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "FirestationNumber [station=" + station + "]";
	}

}
