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
import com.safetynet.api.service.MedicalRecordService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MedicalRecordControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	MedicalRecordService medicalRecordService;

	String firstNameTest = "Paul";
	String lastNameTest = "Lefevre";
	String birthdateTest = "12/03/1988";

	@Test
	public void createMedicalRecord() throws Exception {
		// Given
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
		jsonPerson.set("birthdate", TextNode.valueOf(birthdateTest));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void createMedicalRecordInvalid() throws Exception {
		// Given
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(""));
		jsonPerson.set("birthdate", TextNode.valueOf(birthdateTest));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void createMedicalRecordWhenMedicalRecordAlreadyExist() throws Exception {
		// Given
		Mockito.doThrow(DataAlreadyExistException.class).when(medicalRecordService).createMedicalRecord(Mockito.any());
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();
		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
		jsonPerson.set("birthdate", TextNode.valueOf(birthdateTest));

		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isConflict());
	}
}
