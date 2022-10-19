package com.dolearci.hackerrankchallenges.easy;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LoopsTwo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cycles = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < cycles; i++) {
            List<Integer> line = Arrays.stream(sc.nextLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            int result = line.get(0);
            for (int j = 0; j < line.get(2); j++) {
                result += Math.multiplyExact((int) Math.pow(2, j), line.get(1));
                System.out.print(result + " ");
            }
            System.out.println();
        }
        sc.close();
    }
}
