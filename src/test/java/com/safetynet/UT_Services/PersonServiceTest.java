package com.safetynet.UT_Services;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.api.dao.MedicalRecordDaoImpl;
import com.safetynet.api.dao.PersonDaoImpl;
import com.safetynet.api.exceptions.DataAlreadyExistException;
import com.safetynet.api.exceptions.DataNotFoundException;
import com.safetynet.api.exceptions.InvalidArgumentException;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.MedicalRecordService;
import com.safetynet.api.service.PersonService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonServiceTest {

	@Autowired
	PersonService personServiceTest;

	@MockBean
	PersonDaoImpl personDaoImpl;

	@MockBean
	MedicalRecordDaoImpl medicalrecordDaoImpl;

	@MockBean
	MedicalRecordService medicalrecordServiceMock;

	@MockBean
	DataNotFoundException dataNotFoundExceptionMock;

	@MockBean
	InvalidArgumentException invalidArgumentExceptionMock;

	Person personBoyd = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
			"jaboyd@email.com");

	Person personBauer = new Person("Jack", "Bauer", "1 FBI St.", "L.A.", "59800", "066666", "test1@test.us");

	@Test
	public void createExistingPersonTest() throws Exception {
		// GIVEN
		List<Person> listPerson = new ArrayList<Person>();
		listPerson.add(personBoyd);

		// WHEN
		Mockito.when(personDaoImpl.findAll()).thenReturn(listPerson);

		// THEN
		// On crée un personne qui existe
		try {
			Assertions.assertThat(personServiceTest.createPerson(personBoyd));
			verify(personDaoImpl, Mockito.times(0)).createPerson(personBoyd);
		} catch (DataAlreadyExistException eExp) {
			assert (eExp.getMessage().contains("existe déjà"));
		}
	}

	@Test
	public void createNonExistingPersonTest() throws Exception {
		// GIVEN
		List<Person> listPerson = new ArrayList<Person>();

		// WHEN
		// On retourne une liste vide
		Mockito.when(personDaoImpl.listPerson()).thenReturn(listPerson);

		// THEN
		Assertions.assertThat(personServiceTest.createPerson(personBauer));
		verify(personDaoImpl, Mockito.times(1)).createPerson(personBauer);
	}

	@Test
	public void updateExistingPersonTest() throws Exception {

		// WHEN
		Mockito.when(personDaoImpl.updatePerson(personBoyd)).thenReturn(true);

		// THEN
		Assertions.assertThat(personServiceTest.updatePerson(personBoyd));
		verify(personDaoImpl, Mockito.times(1)).updatePerson(personBoyd);
	}

	@Test
	public void updateNoneExistingPersonTest() throws Exception {
		// when
		Mockito.when(personDaoImpl.updatePerson(personBoyd)).thenReturn(false);

		// THEN
		// On crée un personne qui existe
		try {
			Assertions.assertThat(personServiceTest.updatePerson(personBauer));
			verify(personDaoImpl, Mockito.times(1)).updatePerson(personBauer);
		} catch (DataNotFoundException eExp) {
			assert (eExp.getMessage().contains("n'existe pas"));
		}
	}

	@Test
	public void deleteExistingPersonTest() throws Exception {
		// WHEN
		Mockito.when(personDaoImpl.deletePerson(personBoyd)).thenReturn(true);

		// THEN
		Assertions.assertThat(personServiceTest.deletePerson(personBoyd));
		verify(personDaoImpl, Mockito.times(1)).deletePerson(personBoyd);
	}

	@Test
	public void deleteNonExistingPersonTest() throws Exception {
		// WHEN
		Mockito.when(personDaoImpl.deletePerson(personBauer)).thenReturn(false);

		// THEN
		try {
			Assertions.assertThat(personServiceTest.deletePerson(personBoyd));
			verify(personDaoImpl, Mockito.times(0)).deletePerson(personBoyd);
		} catch (DataNotFoundException eExp) {
			assert (eExp.getMessage().contains("n'existe pas"));
		}
	}

}
