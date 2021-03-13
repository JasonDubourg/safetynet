package com.safetynet.UT_Controller;

import java.util.ArrayList;
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
import com.safetynet.api.dao.MedicalRecordDaoImpl;
import com.safetynet.api.exceptions.DataNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MedicalRecordControllerTest {

	@Autowired
	// MockMvc est un module de SpringTest permettant de simplifier la création de
	// tests Rest
	MockMvc mockMvc;

	@MockBean
	MedicalRecordDaoImpl medicalrecordDao;

	String firstnameTest = "Marc";
	String lastnameTest = "Dupont";
	String birthdate = "12/06/1975";
	List<String> medications = new ArrayList<String>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
	List<String> allergies = new ArrayList<String>(Arrays.asList("nillacilan", "hydrapermazol:100mg"));

	// Création des Medicalrecord
	@Test
	void createMedicalrecordValid() throws Exception {
		// GIVEN
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
		jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
		jsonMedicalrecord.set("lastName", TextNode.valueOf(lastnameTest));
		jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

		// WHEN

		// THEN
		mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isCreated());

	}

	@Test
	void createMedicalrecordInvalid() throws Exception {
		// GIVEN
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
		jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
		jsonMedicalrecord.set("lastName", TextNode.valueOf(""));
		jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

		// WHEN

		// THEN
		mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void updateMedicalrecordInvalid() throws Exception {
		// GIVEN
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
		jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
		jsonMedicalrecord.set("lastName", TextNode.valueOf(""));
		jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

		// WHEN

		// THEN
		mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void updateMedicalrecordWhenMedicalrecordNotExist() throws Exception {
		// GIVEN
		// On courtcircuite l'appel de l'exeption (le fichier Json utilisé en mémoire
		// est vide)
		Mockito.doThrow(DataNotFoundException.class).when(medicalrecordDao).updateMedicalRecord(Mockito.any());

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
		jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
		jsonMedicalrecord.set("lastName", TextNode.valueOf(lastnameTest));
		jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

		// WHEN

		// THEN
		mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	@Test
	void deleteMedicalrecordInvalid() throws Exception {
		// GIVEN
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
		jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
		jsonMedicalrecord.set("lastName", TextNode.valueOf(""));
		jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

		// WHEN

		// THEN
		mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void deleteMedicalrecordWhenvNotExist() throws Exception {
		// GIVEN
		// On courrtcircuite l'appel de l'exeption
		Mockito.doThrow(DataNotFoundException.class).when(medicalrecordDao).deleteMedicalRecord((Mockito.any()));

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
		jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
		jsonMedicalrecord.set("lastName", TextNode.valueOf(lastnameTest));
		jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

		// WHEN

		// THEN
		mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isNotFound());

	}
}
