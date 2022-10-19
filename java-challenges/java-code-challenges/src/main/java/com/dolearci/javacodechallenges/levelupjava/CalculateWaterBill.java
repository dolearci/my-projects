package com.dolearci.javacodechallenges.levelupjava;

import java.util.Scanner;

public class CalculateWaterBill {

	public static double calculateWaterBill(double gallonsUsage) {
		double minimumCharge = 18.84;
		int GallonsInOneCCF = 748;
		double pricePerGallonOverMinimum = 3.90;
		if (gallonsUsage <= 2 * GallonsInOneCCF) return minimumCharge;
		else {
			minimumCharge += ((int) ((gallonsUsage - 1 - 2 * GallonsInOneCCF) / GallonsInOneCCF) + 1) * pricePerGallonOverMinimum;
		}
		return minimumCharge;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("How many gallons of water did you " +
				"use this month?");
		double usage = scanner.nextDouble();
		System.out.println("Your water bill is " +
				calculateWaterBill(usage));
		scanner.close();
	}
}
