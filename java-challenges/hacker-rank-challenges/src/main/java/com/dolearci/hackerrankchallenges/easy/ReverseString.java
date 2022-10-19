package com.dolearci.hackerrankchallenges.easy;

import java.util.Scanner;

public class ReverseString {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder word = new StringBuilder(sc.nextLine());
        StringBuilder reverseWord = new StringBuilder(word);
        reverseWord.reverse();
        if (word.compareTo(reverseWord) == 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
        sc.close();
    }
}
