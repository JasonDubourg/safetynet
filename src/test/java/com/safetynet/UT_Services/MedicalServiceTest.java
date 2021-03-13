package com.safetynet.UT_Services;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.safetynet.api.dao.DataRepository;
import com.safetynet.api.dao.MedicalRecordDaoImpl;
import com.safetynet.api.exceptions.DataAlreadyExistException;
import com.safetynet.api.exceptions.DataNotFoundException;
import com.safetynet.api.exceptions.InvalidArgumentException;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.MedicalRecordService;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MedicalServiceTest {

	@Autowired
	MedicalRecordService medicalRecordService;

	@MockBean
	DataRepository dataRepository;

	@MockBean
	MedicalRecordDaoImpl medicalRecordDaoImpl;

	@MockBean
	DataNotFoundException dataNotFoundException;

	@MockBean
	InvalidArgumentException invalidArgumentException;

	Person personBoyd = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
			"jaboyd@email.com");

	Person personBauer = new Person("Jack", "Bauer", "1 FBI St.", "L.A.", "59800", "066666", "test1@test.us");

	Person personChild = new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
			"jaboyd@email.com");

	List<String> medications = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));
	List<String> allergies = new ArrayList<String>(Arrays.asList("e", "f", "g", "h"));

	MedicalRecord medicalrecordBoyd = new MedicalRecord("Roger", "Boyd", "03/06/1984", medications, allergies);
	MedicalRecord medicalrecordChild = new MedicalRecord("John", "Boyd", "09/06/2017", medications, allergies);
	MedicalRecord medicalrecordUnknown = new MedicalRecord("Jack", "Bauer", "03/01/1984", medications, allergies);
	MedicalRecord medicalrecordEmpty = new MedicalRecord("", "", "03/01/1984", medications, allergies);

	@Test
	void createExistingMedicalRecord() throws Exception {
		// GIVEN
		List<MedicalRecord> medicalRecordList = new ArrayList<>();
		medicalRecordList.add(medicalrecordBoyd);
		medicalRecordList.add(medicalrecordChild);

		// WHEN
		Mockito.when(medicalRecordDaoImpl.findAll()).thenReturn(medicalRecordList);

		// THEN
		try {
			Assertions.assertThat(medicalRecordService.createMedicalRecord(medicalrecordChild));
			// verify(medicalRecordDaoImpl,
			// Mockito.times(0)).createMedicalRecord(medicalrecordChild);
		} catch (DataAlreadyExistException eExp) {
			assert (eExp.getMessage().contains("Le medicalRecord existe"));
		}
	}

	@Test
	public void updateExistingMedicalRecord() throws Exception {
		// GIVEN
		// WHEN
		Mockito.when(medicalRecordDaoImpl.updateMedicalRecord(medicalrecordBoyd)).thenReturn(true);

		// THEN
		Assertions.assertThat(medicalRecordService.updateMedicalRecord(medicalrecordBoyd));
		verify(medicalRecordDaoImpl, Mockito.times(1)).updateMedicalRecord(medicalrecordBoyd);
	}

	@Test
	void updateNonExistingMedicalRecord() throws Exception {
		// GIVEN
		// WHEN
		Mockito.when(medicalRecordDaoImpl.updateMedicalRecord(medicalrecordUnknown)).thenReturn(false);

		// THEN
		try {
			medicalRecordService.updateMedicalRecord(medicalrecordUnknown);
			verify(medicalRecordDaoImpl, Mockito.times(0)).updateMedicalRecord(medicalrecordUnknown);
		} catch (DataNotFoundException dnfe) {
			assert (dnfe.getMessage().contains("n'existe pas"));
		}
	}

	@Test
	void deleteExistingMedicalRecord() throws Exception {
		// GIVEN
		// WHEN
		Mockito.when(medicalRecordDaoImpl.deleteMedicalRecord(medicalrecordBoyd)).thenReturn(true);

		// THEN
		medicalRecordService.deleteMedicalRecord(medicalrecordBoyd);
		verify(medicalRecordDaoImpl, Mockito.times(1)).deleteMedicalRecord(medicalrecordBoyd);
	}

	@Test
	void deleteNonExistingMedicalRecord() throws Exception {
		// GIVEN
		// WHEN
		Mockito.when(medicalRecordDaoImpl.deleteMedicalRecord(medicalrecordUnknown)).thenReturn(false);

		// THEN
		try {
			medicalRecordService.deleteMedicalRecord(medicalrecordUnknown);
			verify(medicalRecordDaoImpl, Mockito.times(2)).deleteMedicalRecord(medicalrecordUnknown);
		} catch (DataNotFoundException dnfe) {
			assert (dnfe.getMessage().contains("n'a pas de dossier médical"));
		}

	}
}
