package com.dolearci.hackerrankchallenges.easy;

import java.util.Arrays;
import java.util.Scanner;

public class AnagramsString {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] firstWord = sc.nextLine().toLowerCase().toCharArray();
        char[] secondWord = sc.nextLine().toLowerCase().toCharArray();
        Arrays.sort(firstWord);
        Arrays.sort(secondWord);
        if (Arrays.equals(firstWord, secondWord)) {
            System.out.println("Anagrams");
        } else {
            System.out.println("Not Anagrams");
        }
        sc.close();
    }
}
