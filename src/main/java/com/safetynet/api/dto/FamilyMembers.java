package com.safetynet.api.dto;

public class FamilyMembers {

	private String firstName; 
	private String lastName;
	private int age;
	private boolean isMinor;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isMinor() {
		return isMinor;
	}
	public void setMinor(boolean isMinor) {
		this.isMinor = isMinor;
	} 
	
}
