package com.dolearci.javacodechallenges.LinkedInLevelUpJava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dolearci.javacodechallenges.LinkedInLevelUpJava.EvenNumber;

class EvenNumberTest {

	@Test
	void isEvenTest() {
		assertTrue(EvenNumber.isEven(0));
		assertTrue(EvenNumber.isEven(2));
		assertTrue(EvenNumber.isEven(4));
		assertTrue(EvenNumber.isEven(6));
		assertTrue(EvenNumber.isEven(8));
		assertTrue(EvenNumber.isEven(-20));
		assertTrue(EvenNumber.isEven(100));
		assertTrue(EvenNumber.isEven(-46));
		assertFalse(EvenNumber.isEven(1));
		assertFalse(EvenNumber.isEven(3));
		assertFalse(EvenNumber.isEven(5));
		assertFalse(EvenNumber.isEven(7));
		assertFalse(EvenNumber.isEven(9));
		assertFalse(EvenNumber.isEven(-11));
		assertFalse(EvenNumber.isEven(93));
		assertFalse(EvenNumber.isEven(-75));
	}
}
