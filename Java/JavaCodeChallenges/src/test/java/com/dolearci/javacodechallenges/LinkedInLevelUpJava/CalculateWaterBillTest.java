package com.dolearci.javacodechallenges.LinkedInLevelUpJava;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.dolearci.javacodechallenges.LinkedInLevelUpJava.CalculateWaterBill;

class CalculateWaterBillTest {

	@Test
	public void calculateWaterBill() {
		assertEquals(22.74, CalculateWaterBill.calculateWaterBill(1800), 0);
		assertEquals(22.74, CalculateWaterBill.calculateWaterBill(2244), 0);
		assertEquals(26.64, CalculateWaterBill.calculateWaterBill(2245), 0);
		assertEquals(26.64, CalculateWaterBill.calculateWaterBill(2992), 0);
		assertEquals(46.14, CalculateWaterBill.calculateWaterBill(6000), 0);
		assertEquals(53.94, CalculateWaterBill.calculateWaterBill(8000), 0);
		assertEquals(18.84, CalculateWaterBill.calculateWaterBill(0), 0);
		assertEquals(18.84, CalculateWaterBill.calculateWaterBill(1496), 0);
		assertEquals(22.74, CalculateWaterBill.calculateWaterBill(1497), 0);
		assertEquals(18.84, CalculateWaterBill.calculateWaterBill(-20), 0);
	}
}
