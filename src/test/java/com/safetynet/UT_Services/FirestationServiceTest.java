package com.safetynet.UT_Services;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.api.dao.FirestationDaoImpl;
import com.safetynet.api.exceptions.DataAlreadyExistException;
import com.safetynet.api.exceptions.DataNotFoundException;
import com.safetynet.api.model.Firestation;
import com.safetynet.api.service.FirestationService;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FirestationServiceTest {

	@MockBean
	FirestationDaoImpl firestationDaoImpl;

	@Autowired
	FirestationService firestationService;

	Firestation firestation1 = new Firestation("WhiteHouse", "1");
	Firestation firestation2 = new Firestation("BlackHouse", "2");
	Firestation firestationValid = new Firestation("29 15th St", "2");
	Firestation firestationVide = new Firestation("", "");

	@Test
	public void createExistingFirestationTest() throws Exception {
		// GIVEN
		List<Firestation> listFirestation = new ArrayList<Firestation>();
		listFirestation.add(firestation1);

		// WHEN
		Mockito.when(firestationDaoImpl.findAll()).thenReturn(listFirestation);

		// THEN
		// On crée une station qui existe
		try {
			Assertions.assertFalse(firestationService.createFirestation(firestation1));
			verify(firestationDaoImpl, Mockito.times(0)).createFirestation(firestation1);
		} catch (DataAlreadyExistException eExp) {
			assert (eExp.getMessage().contains("existe déja"));
		}
	}

	@Test
	public void createValidFirestationTest() throws Exception {
		// given
		List<Firestation> listFirestation = new ArrayList<Firestation>();
		// when
		Mockito.when(firestationDaoImpl.findAll()).thenReturn(listFirestation);

		// then
		Assertions.assertTrue(firestationService.createFirestation(firestation1));
		verify(firestationDaoImpl, Mockito.times(1)).createFirestation(firestation1);
	}

	@Test
	public void updateNonExistingFirestationTest() throws Exception {
		// GIVEN
		// WHEN
		Mockito.when(firestationDaoImpl.updateFirestation(firestation1)).thenReturn(false);
		// THEN
		// On crée une station qui existe
		try {
			Assertions.assertFalse(firestationService.updateFirestation(firestation1));
			verify(firestationDaoImpl, Mockito.times(1)).updateFirestation(firestation1);
		} catch (DataNotFoundException dnfe) {
			assert (dnfe.getMessage().contains("n'existe pas"));
		}
	}

	@Test
	public void deleteNonExistingFirestationTest() throws Exception {
		// GIVEN
		// WHEN
		Mockito.when(firestationDaoImpl.deleteFirestation(firestation1)).thenReturn(false);
		// THEN
		// On crée une station qui existe
		try {
			Assertions.assertFalse(firestationService.deleteFirestation(firestation1));
			verify(firestationDaoImpl, Mockito.times(1)).deleteFirestation(firestation1);
		} catch (DataNotFoundException dnfe) {
			assert (dnfe.getMessage().contains("pas enregistré."));
		}
	}

}
