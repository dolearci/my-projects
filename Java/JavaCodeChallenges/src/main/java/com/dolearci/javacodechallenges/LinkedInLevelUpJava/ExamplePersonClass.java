package com.dolearci.javacodechallenges.LinkedInLevelUpJava;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
public class ExamplePersonClass {
	private String firstName;
	private String lastName;
	private int age;
	private String Gender;

	public void PersonGreeting() {
		System.out.println("Hi, I am " + getFirstName() + " " + getLastName() + ". I am a " + getGender() + " and I have " + getAge() + " years.");
	}
}
