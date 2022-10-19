package com.dolearci.hackerrankchallenges.easy;

import java.util.Scanner;

public class UserNameRegex {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cycles = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < cycles; i++) {
            String line = sc.nextLine();
            if (line.matches("[a-zA-Z][a-zA-Z0-9_]+") && line.length() >= 8 && line.length() <= 30) {
                System.out.println("Valid");
            } else {
                System.out.println("Invalid");
            }
        }
        sc.close();
    }
}
