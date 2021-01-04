package com.safetynet.api.dao;

import java.util.List;

import com.safetynet.api.model.Firestation;

public interface IFirestationDao {

	public List<Firestation>findAll();
	public List<String>findFirestationByNumberStation(String station);
	public List<String> findFirestationNumberbyAddress(String address);
	
}
