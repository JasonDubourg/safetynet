package com.safetynet.UT_Controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.safetynet.api.exceptions.DataAlreadyExistException;
import com.safetynet.api.exceptions.DataNotFoundException;
import com.safetynet.api.service.FirestationService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class FirestationControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	FirestationService firestationService;

	String addressTest = "Rue de la Paix";
	String stationTest = "5";

	@Test
	public void createFirestation() throws Exception {
		// Given
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();
		jsonFirestation.set("address", TextNode.valueOf(addressTest));
		jsonFirestation.set("station", TextNode.valueOf(stationTest));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.post("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void createFirestationInvalid() throws Exception {
		// Given
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();
		jsonFirestation.set("address", TextNode.valueOf(addressTest));
		jsonFirestation.set("station", TextNode.valueOf(""));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(addressTest.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void createFirestationWhenFirestationAlreadyExist() throws Exception {
		// Given
		Mockito.doThrow(DataAlreadyExistException.class).when(firestationService).createFirestation(Mockito.any());
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();
		jsonFirestation.set("address", TextNode.valueOf(addressTest));
		jsonFirestation.set("station", TextNode.valueOf(stationTest));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.post("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isConflict());
	}

	@Test
	public void updateFirestationValid() throws Exception {
		// Given
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();
		jsonFirestation.set("address", TextNode.valueOf(addressTest));
		jsonFirestation.set("station", TextNode.valueOf(stationTest));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.put("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void updateFirestationInvalid() throws Exception {
		// Given
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();
		jsonFirestation.set("address", TextNode.valueOf(""));
		jsonFirestation.set("station", TextNode.valueOf(""));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.put("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void updateFirestationWhenNotFound() throws Exception {
		Mockito.doThrow(DataNotFoundException.class).when(firestationService).updateFirestation(Mockito.any());

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		jsonFirestation.set("station", TextNode.valueOf(stationTest));
		jsonFirestation.set("address", TextNode.valueOf(addressTest));

		mockMvc.perform(MockMvcRequestBuilders.put("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void deleteFirestationValid() throws Exception {
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// GIVEN
		jsonFirestation.set("station", TextNode.valueOf(stationTest));
		jsonFirestation.set("address", TextNode.valueOf(addressTest));

		// WHEN
		// THEN
		mockMvc.perform(MockMvcRequestBuilders.delete("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isResetContent());
	}

	@Test
	void deleteFirestationValidByAddress() throws Exception {
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// GIVEN
		jsonFirestation.set("station", TextNode.valueOf(stationTest));
		jsonFirestation.set("address", TextNode.valueOf(addressTest));

		// WHEN
		// THEN
		mockMvc.perform(MockMvcRequestBuilders.delete("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isResetContent());
	}

	@Test
	void deleteFirestationWhenFirestationNotFound() throws Exception {
		Mockito.doThrow(DataNotFoundException.class).when(firestationService).deleteFirestation(Mockito.any());

		// GIVEN
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// WHEN
		// THEN
		jsonFirestation.set("station", TextNode.valueOf(stationTest));
		jsonFirestation.set("address", TextNode.valueOf(addressTest));
		mockMvc.perform(MockMvcRequestBuilders.delete("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void FirestationController() throws Exception {
		// Test 1 : on envoie une requête GET avec en paramètre une adresse valide
		// + on vérifie que le statut de la réponse est 200
		mockMvc.perform(MockMvcRequestBuilders.get("/fire").param("address", "892 Downing Ct"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Test 2 : on vérifie que le service a bien été appelé avec les bons paramètres
		Mockito.verify(firestationService, Mockito.times(1))
				.getListPersonsAndNumberFirestationByAddress("892 Downing Ct");

		// Test 3 : on envoie une requête GET avec en paramètre une adresse non valide
		// + on vérifie que le retour est vide
		mockMvc.perform(MockMvcRequestBuilders.get("/fire").param("address", "999 Downing Ct"))
				.andExpect(MockMvcResultMatchers.content().string(""));
	}

	@Test
	void getFireStationListPhone() throws Exception {
		// Test 1 : on envoie une requête GET avec en paramètre un n° de station
		// valide
		// + on vérifie que le statut de la réponse est 200
		mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert").param("firestation", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Test 2 : on vérifie que le service a bien été appelé avec les bons
		// paramètres
		Mockito.verify(firestationService, Mockito.times(1)).getPersonsPhoneNumbersByFirestationNumber("1");

		// Test 3 : on envoie une requête GET avec en paramètre une station
		// qui n'existe pas
		// + on vérifie que le retour est vide
		mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert").param("firestation", "9"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	void getFireStationCoveragePerson() throws Exception {
		// Test 1 : on envoie une requête GET avec en paramètre un n° de station
		// valide
		// + on vérifie que le statut de la réponse est 200
		mockMvc.perform(MockMvcRequestBuilders.get("/firestation").param("stationNumber", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Test 2 : on vérifie que le service a bien été appelé avec les bons
		// paramètres
		Mockito.verify(firestationService, Mockito.times(1)).getFirestationWithPersonInfo("1");

		// Test 3 : on envoie une requête GET avec en paramètre une station
		// qui n'existe pas
		// + on vérifie que le retour est vide
		mockMvc.perform(MockMvcRequestBuilders.get("/firestation").param("stationNumber", "9"))
				.andExpect(MockMvcResultMatchers.content().string(""));
	}

	@Test
	void getFireStationPersonAtAddress() throws Exception {
		List<String> stations = Arrays.asList("1", "2");
		// Test 1 : on envoie une requête GET avec en paramètre des n° de station valide
		// + on vérifie que le statut de la réponse est 200
		mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations").param("stationNumber", "1", "2"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Test 2 : on vérifie que le service a bien été appelé avec les bons
		// paramètres
		Mockito.verify(firestationService, Mockito.times(1)).getFirestationCoveragePerson(stations);

		// Test 3 : on envoie une requête GET avec en paramètre une station
		// qui n'existe pas
		// + on vérifie que le retour est vide
		mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations").param("stations", "0"))
				.andExpect(MockMvcResultMatchers.content().string(""));
	}
}
