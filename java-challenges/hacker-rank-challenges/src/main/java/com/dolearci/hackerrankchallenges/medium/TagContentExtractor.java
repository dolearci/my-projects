/*
Tag Content Extractor
In a tag based language, like XML or HTML, contents are enclosed by a start tag and an end tag. For example:
<tag>contents</tag>
In this problem, you will be given a text in a tag based language. Your task is to parse this text and retrieve
the contents which are enclosed by well organized tag sequences. Well organized tags maintain the following constraints:
The name of the start and end tag must be same. The following HTML code is not valid:
<h1>Hello World</h2>
Tag can be nested, but there will be no content in between the nested tags. The following code is not valid:
<h1><a>contents</a>invalid</h1>
Tags can consist of any printable characters.
Input Format
The first line of input contains a single integer N, representing the number of lines. The next N lines
contains a line of text.
Constraints
1<=N<=100
Each line contains at most 104 printable characters. The total number of characters in all test cases will not exceed 106.
Output Format
For each line, print the valid content enclosed by proper tags. If there is multiple valid content in a test case,
print out each of the valid content on separate lines. If no valid content is found in a test case, print "None" without quotes.
Sample Input
4
<h1>Nayeem loves counseling</h1>
<h1><h1>Sanjay has no watch</h1></h1><par>So wait for a while<par>
<Amee>safat codes like a ninja</amee>
<SA premium>Imtiaz has a secret crush</SA premium>
Sample Output
Nayeem loves counseling
Sanjay has no watch
So wait for a while
None
Imtiaz has a secret crush
*/

package com.dolearci.hackerrankchallenges.medium;

import java.util.Scanner;

public class TagContentExtractor {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cycles = Integer.parseInt(sc.nextLine());
        int startTag;
        int endTag;
        String startWord;
        String endWord;
        boolean printed;
        for (int i = 0; i < cycles; i++) {
            String line = sc.nextLine();
            printed = false;
            while (line.contains("<") && line.contains("</") && line.contains(">")) {
                startTag = line.indexOf('<');
                endTag = line.indexOf('>');
                startWord = line.substring(startTag + 1, endTag);
                line = line.substring(endTag + 1);
                if (line.indexOf('<') < line.indexOf("</")) {
                    continue;
                }
                if (!line.contains("</") || !line.contains(">"))
                    continue;
                startTag = line.indexOf("</");
                endTag = line.indexOf('>');
                endWord = line.substring(startTag + 2, endTag);
                if (startWord.equals(endWord) && !line.substring(0, startTag).isEmpty() && !startWord.isEmpty()) {
                    System.out.println(line.substring(0, startTag));
                    printed = true;
                }
                line = line.substring(endTag + 1);
            }
            if (!printed) {
                System.out.println("None");
            }
        }
        sc.close();
    }
}
