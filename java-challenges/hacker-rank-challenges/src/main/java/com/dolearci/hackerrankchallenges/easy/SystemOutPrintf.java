package com.dolearci.hackerrankchallenges.easy;

import java.text.DecimalFormat;
import java.util.Scanner;

public class SystemOutPrintf {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("000");
        System.out.println("================================");
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String lang = line.substring(0, line.indexOf(' '));
            int number = Integer.parseInt(line.substring(lang.length() + 1));
            System.out.printf("%s%s%03d%n", lang, String.format("%" + Math.subtractExact(15, lang.length()) + "s", " "), Integer.parseInt(df.format(number)));
        }
        System.out.println("================================");
        sc.close();
    }
}
