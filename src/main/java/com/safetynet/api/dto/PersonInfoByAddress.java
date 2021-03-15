package com.safetynet.api.dto;

import java.util.ArrayList;
import java.util.List;

public class PersonInfoByAddress {

	private String lastName;
	private String phone;
	private int age;
	private List<String> medications = new ArrayList<>();
	private List<String> allergies = new ArrayList<>();

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<String> getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

	@Override
	public String toString() {
		return "PersonInfoByAddress [lastName=" + lastName + ", phone=" + phone + ", age=" + age + ", medications="
				+ medications + ", allergies=" + allergies + "]";
	}

}
