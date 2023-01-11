package com.dolearci.javacodechallenges.LinkedInLevelUpJava;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SecretMessage {

	public static void redactTextFile(String fileName, String[] redactedWordsArray) throws IOException {

		File givenFile = new File("src/main/resources/" + fileName);
		File redactedFile = new File("src/main/resources/redacted-" + fileName);
		List<String> words;

		if (!fileName.contains(".txt") || !givenFile.exists()) {
			System.out.println("This is not a text file.");
			return;
		}

		if (redactedFile.exists()) {
			redactedFile.delete();
		}
		redactedFile.createNewFile();
		FileWriter redactedWriter = new FileWriter(redactedFile);
		Scanner myReader = new Scanner(givenFile);

		while (myReader.hasNextLine()) {
			words = Arrays.stream(myReader.nextLine().split(" ")).toList();
			for (String word : words) {
				if (Arrays.asList(redactedWordsArray).contains(word)) {
					redactedWriter.write("REDACTED ");
				} else {
					redactedWriter.write(word + " ");
				}
			}
			redactedWriter.write("\n");
		}
		redactedWriter.close();
		myReader.close();
	}

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);

		System.out.println("What file would you like to " +
				"redact information from?");
		String fileName = scanner.nextLine();

		System.out.println("What words would you like to redact? " +
				"Separate each word or phrase with a comma. " +
				"If you phrase includes punctuation, include " +
				"that in your input.");
		String redactedWords = scanner.nextLine();
		String[] redactedWordsList = redactedWords.split(",");

		redactTextFile(fileName, redactedWordsList);

		scanner.close();
	}
}
