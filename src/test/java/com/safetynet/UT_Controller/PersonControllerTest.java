package com.safetynet.UT_Controller;

import java.util.Arrays;

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
import com.safetynet.api.service.PersonService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PersonControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	PersonService personService;

	String firstName = "Pomp";
	String firstNameTest = "John";
	String lastNameTest = "Boyd";
	String addressTest = "1509 Culver St";
	String cityTest = "L.A.";
	String zipTest = "59800";
	String phoneTest = "0666666";
	String emailTest = "test@test.us";

	@Test
	public void createPersonValid() throws Exception {
		// Given
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void createPersonWhenPersonAlreadyExist() throws Exception {
		// Given
		Mockito.doThrow(DataAlreadyExistException.class).when(personService).createPerson(Mockito.any());
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isConflict());
	}

	@Test
	public void updatePersonValid() throws Exception {
		// Given
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.put("/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void updatePersonInvalid() throws Exception {
		// Given
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(""));
		jsonPerson.set("lastName", TextNode.valueOf(""));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.put("/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void updatePersonWhenNotFound() throws Exception {
		// Given
		Mockito.doThrow(DataNotFoundException.class).when(personService).updatePerson(Mockito.any());
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.put("/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void deletePersonValid() throws Exception {
		// Given
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.delete("/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isResetContent());
	}

	@Test
	public void deletePersonInvalid() throws Exception {
		// Given
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(""));
		jsonPerson.set("lastName", TextNode.valueOf(""));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.delete("/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void deletePersonWhenPersonNotFound() throws Exception {
		// Given
		Mockito.doThrow(DataNotFoundException.class).when(personService).deletePerson(Mockito.any());
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.delete("/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void getCommunityEmail() throws Exception {
		// On simule le comportement du service de requête get avec en retour des
		// valeurs d'emails
		Mockito.when(personService.getPersonEmailByCity("Culver")).thenReturn(Arrays.asList("a@a", "b@b", "c@c"));
		// Vérifie que le retour de la requête est une réponse 200
		mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail").param("city", "Culver"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		// On vérifie que le service a bien été appelé avec le bon paramètre
		Mockito.verify(personService, Mockito.times(1)).getPersonEmailByCity("Culver");
	}

	@Test
	void getChildByAddress() throws Exception {

		// Test 1 : on envoie une requête GET avec en paramètre une adresse valide
		// + on vérifie que le statut de la réponse est 200
		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert").param("address", "1509 Culver St"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Test 2 : on vérifie que le service a bien été appelé avec les bons paramètres
		Mockito.verify(personService, Mockito.times(1)).getChildAndFamilyMembersByAddress("1509 Culver St");

		// Test 3 : on envoie une requête GET avec en paramètre une adresse non valide
		// + on vérifie que le retour est vide
		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert").param("address", "999 Culver St"))
				.andExpect(MockMvcResultMatchers.content().string(""));

		// Test 4 : on envoie une requête GET avec en paramètre une adresse sans enfants
		// + on vérifie que le retour est vide
		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert").param("address", "908 73rd St"))
				.andExpect(MockMvcResultMatchers.content().string(""));
	}

	@Test
	void getPersonInfo() throws Exception {
		// On envoie une requête GET
		// + on vérifie que le statut de la réponse est 200
		mockMvc.perform(MockMvcRequestBuilders.get("/personInfo").param("lastName", "Boyd"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
