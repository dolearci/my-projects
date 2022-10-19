package com.dolearci.hackerrankchallenges.easy;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PatternCompile {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cycles = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < cycles; i++) {
            try {
                Pattern.compile(sc.nextLine());
                System.out.println("Valid");
            } catch (PatternSyntaxException exception) {
                System.out.println("Invalid");
            }
        }
        sc.close();
    }
}
