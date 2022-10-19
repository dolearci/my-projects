package com.dolearci.javacodechallenges.LinkedInLevelUpJava;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.dolearci.javacodechallenges.LinkedInLevelUpJava.PasswordComplexity;

class PasswordComplexityTest {
	@Test
	public void isPasswordComplexTrueTest() {
		assertTrue(PasswordComplexity.isPasswordComplex("Happy12"));
		assertTrue(PasswordComplexity.isPasswordComplex("1SarahL ength"));
		assertTrue(PasswordComplexity.isPasswordComplex("Password12"));
		assertTrue(PasswordComplexity.isPasswordComplex("2PassW@ord1"));
		assertTrue(PasswordComplexity.isPasswordComplex("01Sall#y9"));
	}

	@Test
	public void isPasswordComplexFalseTest() {
		assertFalse(PasswordComplexity.isPasswordComplex("12aB "));
		assertFalse(PasswordComplexity.isPasswordComplex("Ab112"));
		assertFalse(PasswordComplexity.isPasswordComplex("happy"));
		assertFalse(PasswordComplexity.isPasswordComplex("happy_1"));
		assertFalse(PasswordComplexity.isPasswordComplex("p assword12"));
		assertFalse(PasswordComplexity.isPasswordComplex("Sur18"));
		assertFalse(PasswordComplexity.isPasswordComplex(""));
	}
}
