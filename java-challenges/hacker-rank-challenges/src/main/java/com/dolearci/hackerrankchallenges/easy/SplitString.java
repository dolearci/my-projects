package com.dolearci.hackerrankchallenges.easy;

import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SplitString {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> words = Pattern.compile("[a-zA-Z]+")
                .matcher(sc.nextLine())
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
        System.out.println(words.size());
        for (String word : words) {
            System.out.println(word);
        }
        sc.close();
    }
}
