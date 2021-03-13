package com.safetynet.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.dao.DataRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonIT {
	// On utilise cet objet pour envoyer des requetes a notre serveur Spring
	// Boot
	@Autowired
	TestRestTemplate clientRest;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	DataRepository dataRepository;

	@BeforeEach
	void initDb() throws Exception {
		dataRepository.init();
		// Modification Db non autorisée
		dataRepository.setCommit(false);
	}

	@Test
	void getCommunityEmail() throws Exception {
		// On envoie une requête GET avec en paramètre la ville Culver
		// + on vérifie que le statut de la réponse est 200
		ResponseEntity<String> response = clientRest.getForEntity("/communityEmail?city=Culver", String.class);
		System.out.println(response);
		// on vérifie le code retour 200
		assertEquals(HttpStatus.OK, response.getStatusCode());
		// renvoie un jsonnode qu'on attend
		System.out.println(ClassLoader.getSystemResourceAsStream("culvercommunityEmail.json"));
		JsonNode expectedJson = objectMapper
				.readTree(ClassLoader.getSystemResourceAsStream("culvercommunityEmail.json"));
		// on vérifie le contenu
		assertEquals(expectedJson, objectMapper.readTree(response.getBody()));
	}

	@Test
	void getPersonInfoWithBoyd() throws Exception {
		ResponseEntity<String> response = clientRest.getForEntity("/personInfo?lastName=Boyd", String.class);

		// on vérifie le code retour 200
		assertEquals(HttpStatus.OK, response.getStatusCode());
		JsonNode expectedJson = objectMapper.readTree(ClassLoader.getSystemResourceAsStream("personInfoBoyd.json"));
		JsonNode json = objectMapper.readTree(response.getBody());

		assertEquals(expectedJson, json);
	}

	@Test
	void getChildAlertWithChild() throws Exception {
		ResponseEntity<String> response = clientRest.getForEntity("/childAlert?address=1509 Culver St", String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		JsonNode expectedJson = objectMapper
				.readTree(ClassLoader.getSystemResourceAsStream("childAlert1509CulverSt.json"));

		JsonNode json = objectMapper.readTree(response.getBody());

		assertEquals(expectedJson, json);
	}
}
