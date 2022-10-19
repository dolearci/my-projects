package com.dolearci.hackerrankchallenges.easy;

import java.util.Scanner;

public class ScannerAndIf {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int breadth = Integer.parseInt(sc.nextLine());
        int height = Integer.parseInt(sc.nextLine());
        if (breadth <= 0 || height <= 0) {
            System.out.println("java.lang.Exception: Breadth and height must be positive");
        } else {
            System.out.println(breadth * height);
        }
        sc.close();
    }
}
