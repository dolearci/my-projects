package com.dolearci.javacodechallenges.levelupjava;

import java.util.Scanner;

public class PasswordComplexity {

	public static boolean isPasswordComplex(String password) {
		boolean number = false;
		boolean lower = false;
		boolean upper = false;
		if (password.length() > 5) {
			char[] chars = password.toCharArray();
			for (char ch : chars) {
				if (Character.isLowerCase(ch)) lower = true;
				if (Character.isUpperCase(ch)) upper = true;
				if (Character.isDigit(ch)) number = true;
			}
		}
		return number && lower && upper;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter a password: ");
		String userInput = scanner.nextLine();
		System.out.println("Is the password complex? "
				+ isPasswordComplex(userInput));

		scanner.close();
	}
}
