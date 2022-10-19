package com.dolearci.hackerrankchallenges.easy;

import java.util.Scanner;

public class SubstringComparison {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String word = sc.nextLine();
        int numberOfChars = sc.nextInt();
        String minWord = word.substring(0, numberOfChars);
        String maxWord = word.substring(0, numberOfChars);
        String loopWord = word.substring(0, word.length() - numberOfChars + 1);
        for (int i = 0; i < loopWord.length(); i++) {
            String substring = word.substring(i, numberOfChars + i);
            if (minWord.compareTo(substring) > 0) {
                minWord = substring;
            }
            if (maxWord.compareTo(substring) < 0) {
                maxWord = substring;
            }
        }
        System.out.println(minWord);
        System.out.println(maxWord);
        sc.close();
    }
}
