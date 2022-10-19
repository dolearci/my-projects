package com.dolearci.hackerrankchallenges.easy;

import java.util.Scanner;

public class IntToString {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            String.valueOf(sc.nextInt());
            System.out.println("Good job");
        } catch (Exception e) {
            System.out.println("Wrong answer");
        }
        sc.close();
    }
}
