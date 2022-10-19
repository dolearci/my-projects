package com.dolearci.javacodechallenges.LinkedInLevelUpJava;

import java.util.Scanner;

public class DoubleOrNothing {

	private static int balance = 10;

	public static void Doubling() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you want to double balance? Currently you have " + balance + " (write any word or character to continue / write n to leave with your balance)");
		String answer = sc.nextLine();
		if (answer.equals("n")) System.exit(0);
		if (Math.random() * 100 > Math.random() * 100) {
			balance *= 2;
			System.out.println("You won! balance is " + balance);
		} else {
			balance = 10;
			System.out.println("You lost! balance is " + balance);
		}
	}

	public static void main(String[] args) {
		while (true) {
			Doubling();
		}
	}
}
