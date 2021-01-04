package com.safetynet.api.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class CalculAge {

	public static int calculAgeWithBirthdate(String birthdate) {
		// On détermine le format.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		// On transforme en date local.
		LocalDate bDate = LocalDate.parse(birthdate, formatter); 
		LocalDate today = LocalDate.now(); 
		// On calcul la différence entre les deux date et on retourne en années.
		return Period.between(bDate, today).getYears(); 
	}
}
