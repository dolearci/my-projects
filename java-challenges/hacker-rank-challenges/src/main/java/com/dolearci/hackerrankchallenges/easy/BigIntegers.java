package com.dolearci.hackerrankchallenges.easy;

import java.math.BigInteger;
import java.util.Scanner;

public class BigIntegers {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BigInteger firstNumber = new BigInteger(sc.nextLine());
        BigInteger secondNumber = new BigInteger(sc.nextLine());
        System.out.println(firstNumber.add(secondNumber));
        System.out.println(firstNumber.multiply(secondNumber));
        sc.close();
    }
}
