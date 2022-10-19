package com.dolearci.hackerrankchallenges.easy;

import java.util.Scanner;

public class StringSubstring {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String word = sc.nextLine();
        int startPos = sc.nextInt();
        int endPos = sc.nextInt();
        System.out.println(word.substring(startPos, endPos));
        sc.close();
    }
}
