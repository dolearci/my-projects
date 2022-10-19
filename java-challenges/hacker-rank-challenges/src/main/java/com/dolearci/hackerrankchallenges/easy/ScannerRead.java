package com.dolearci.hackerrankchallenges.easy;

import java.util.Scanner;

public class ScannerRead {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int intNumber = sc.nextInt();
        double doubleNumber = sc.nextDouble();
        sc.nextLine();
        String string = sc.nextLine();
        System.out.println("String: " + string);
        System.out.println("Double: " + doubleNumber);
        System.out.println("Int: " + intNumber);
        sc.close();
    }
}
