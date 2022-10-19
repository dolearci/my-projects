package com.dolearci.javacodechallenges.levelupjava;

import java.time.LocalDate;

public class WhatsTheDayInFuture {

	public static void main( String[] args )
	{
		System.out.println("100 days from now is... " +
				WhatsTheDay(100));
	}

	public static LocalDate WhatsTheDay(int numberOfDays) {
		return LocalDate.now().plusDays(numberOfDays);
	}
}
