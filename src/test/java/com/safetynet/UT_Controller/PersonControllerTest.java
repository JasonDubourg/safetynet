package com.safetynet.UT_Controller;

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
import com.safetynet.api.service.PersonService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PersonControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	PersonService personService;
	
	String firstNameTest = "John";
	String lastNameTest = "Boyd";
	String addressTest = "1 FBI St.";
	String cityTest = "L.A.";
	String zipTest = "59800";
	String phoneTest = "0666666";
	String emailTest = "test@test.us";
	
	@Test
	void createPersonValid() throws Exception {
		// Given 
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
		
		// When 
		
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	void createPersonInvalid() throws Exception {
		//Given
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(""));
		
		// When 
		
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void createPersonWhenPersonAlreadyExist() throws Exception {
		//Given
		Mockito.doThrow(DataAlreadyExistException.class).when(personService).createPerson(Mockito.any());
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
		
		// When 
		
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isConflict());
	}
	
}
