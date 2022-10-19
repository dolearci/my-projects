package com.dolearci.javacodechallenges.levelupjava;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentVolunteer {

	public static List<String> findStudentsWithIncompleteVolunteerEvents(List<String> students, Map<String, List<String>> attendeesMapping) {
		List<String> incompleteStudents = new ArrayList<>();
		for (String student : students) {
			int counter = 0;
			for (Map.Entry<String, List<String>> event : attendeesMapping.entrySet()) {
				if (event.getValue().contains(student)) {
					counter += 1;
				}
			}
			if (counter < 2) {
				incompleteStudents.add(student);
			}
		}
		return incompleteStudents;
	}

	public static void main(String[] args) {
		List<String> students = List.of("Sally", "Polly", "Molly",
				"Tony", "Harry");

		Map<String, List<String>> attendeesMapping = Map.of("Farmer's Market", List.of("Sally", "Polly"),
				"Car Wash Fundraiser", List.of("Molly", "Tony", "Polly"),
				"Cooking Workshop", List.of("Sally", "Molly", "Polly"),
				"Midnight Breakfast", List.of("Polly", "Molly"));

		System.out.println(findStudentsWithIncompleteVolunteerEvents(
				students, attendeesMapping));
	}
}
