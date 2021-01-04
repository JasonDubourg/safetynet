package com.safetynet.api.dao;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.Database;

@Repository
public class DataRepository {

	// Permet de mapper un Json en objet Java.
	private static final ObjectMapper objectMapper = new ObjectMapper(); 
	
	public static Database database; 

	// On va lire le fichier Json
	public DataRepository() throws IOException {
		InputStream ips = ClassLoader.getSystemResourceAsStream("data.json"); 
		// l'object mapper écrit dans l'objet database ce qu'il voit dans le Json.
		database = objectMapper.readerFor(Database.class).readValue(ips); 
	}
	
}
