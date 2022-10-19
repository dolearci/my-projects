package com.dolearci.hackerrankchallenges.easy;

import java.math.BigInteger;
import java.util.Scanner;

public class DatatypesNumbers {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            BigInteger number = new BigInteger(sc.nextLine());
            if (number.bitLength() >= 64) {
                System.out.println(number + " can't be fitted anywhere.");
                continue;
            }
            System.out.println(number + " can be fitted in:");
            if (number.bitLength() < 8) {
                System.out.println("* byte" + number.bitLength());
            }
            if (number.bitLength() < 16) {
                System.out.println("* short" + number.bitLength());
            }
            if (number.bitLength() < 32) {
                System.out.println("* int" + number.bitLength());
            }
            if (number.bitLength() < 64) {
                System.out.println("* long" + number.bitLength());
            }
        }
        sc.close();
    }
}
