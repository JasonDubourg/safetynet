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
		// Given
		Mockito.doThrow(DataAlreadyExistException.class).when(firestationService).deleteFirestation(Mockito.any());
		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();
		jsonFirestation.set("address", TextNode.valueOf(addressTest));
		jsonFirestation.set("station", TextNode.valueOf(stationTest));
		// When

		// Then
		mockMvc.perform(MockMvcRequestBuilders.delete("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
