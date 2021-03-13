package com.safetynet.UT_Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import com.safetynet.api.dao.PersonDaoImpl;
import com.safetynet.api.exceptions.DataAlreadyExistException;
import com.safetynet.api.exceptions.DataNotFoundException;
import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.FirestationService;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FirestationServiceTest {

	@MockBean
	FirestationDaoImpl firestationDaoImpl;

	@MockBean
	PersonDaoImpl personDaoImpl;

	@Autowired
	FirestationService firestationService;

	Firestation firestation1 = new Firestation("WhiteHouse", "1");
	Firestation firestation2 = new Firestation("BlackHouse", "1");
	Firestation firestationValid = new Firestation("29 15th St", "2");
	Firestation firestationVide = new Firestation("", "");

	String addressTest = "Chemin Burgos";

	Person personTest1 = new Person("Jason", "Dubourg", "Chemin Burgos", "Bordeaux", "33140", "888", "j@d");
	Person personTest2 = new Person("Tim", "Dubourg", "Chemin Burgos", "Bordeaux", "33140", "444", "t@d");

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

	@Test
	public void getFireStationCoveragePerson() throws Exception {
		// GIVEN
		List<Firestation> listFireStationAddressTest = Arrays.asList(firestation1, firestation2);
		// WHEN
		Mockito.when(firestationDaoImpl.findFirestationByStation(firestation1.getStation()))
				.thenReturn(listFireStationAddressTest);
		List<String> stationsStr = listFireStationAddressTest.stream().map(firestation -> firestation.getStation())
				.collect(Collectors.toList());
		// THEN
		assertThat(firestationService.getFirestationCoveragePerson(stationsStr).size()).isEqualTo(4);
	}

	@Test
	public void getListPersonsAndNumberFirestationByAddress() throws Exception {
		// GIVEN
		List<String> listFireStationAddressTest = Arrays.asList(firestation1.getStation(), firestation2.getAddress());

		// WHEN
		Mockito.when(firestationDaoImpl.findFirestationNumberbyAddress(addressTest))
				.thenReturn(listFireStationAddressTest);

		// THEN
		assertThat(firestationService.getListPersonsAndNumberFirestationByAddress(addressTest)
				.equals(listFireStationAddressTest));

	}
}
