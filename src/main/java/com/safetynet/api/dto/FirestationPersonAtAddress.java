package com.safetynet.api.dto;

import java.util.ArrayList;
import java.util.List;

public class FirestationPersonAtAddress {

	private String station;
	private String address;
	private List <PersonInfoByStation> listPersonInfo = new ArrayList<>();
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List <PersonInfoByStation> getListPersonInfo() {
		return listPersonInfo;
	}
	public void setListPersonInfo(List <PersonInfoByStation> listPersonInfo) {
		this.listPersonInfo = listPersonInfo;
	}
}
