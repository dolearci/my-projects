package com.dolearci.hackerrankchallenges.easy;

import java.util.Scanner;

public class StringOperations {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String wordOne = sc.nextLine();
        wordOne = Character.toUpperCase(wordOne.charAt(0)) + wordOne.substring(1);
        String wordTwo = sc.nextLine();
        wordTwo = Character.toUpperCase(wordTwo.charAt(0)) + wordTwo.substring(1);
        System.out.println(Math.addExact(wordOne.length(), wordTwo.length()));
        if (wordOne.compareTo(wordTwo) > 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
        System.out.println(wordOne + " " + wordTwo);
        sc.close();
    }
}
