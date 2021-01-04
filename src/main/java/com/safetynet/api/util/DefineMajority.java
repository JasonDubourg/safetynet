package com.safetynet.api.util;

public class DefineMajority {

	public static boolean isMinor(int age) {
		if(age <= 18) {
			return true; 
		}
		else {
			return false; 
		}
	}
}
