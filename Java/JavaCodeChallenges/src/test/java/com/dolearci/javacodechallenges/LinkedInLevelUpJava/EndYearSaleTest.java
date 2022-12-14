package com.dolearci.javacodechallenges.LinkedInLevelUpJava;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.dolearci.javacodechallenges.LinkedInLevelUpJava.EndYearSale;

import java.util.List;
import java.util.Optional;

class EndYearSaleTest {
	@Test
	public void getSetName() {
		EndYearSale.StoreItem recordPlayerItem = new EndYearSale.StoreItem("Record Player", 108.50, .65);
		assertEquals("Record Player", recordPlayerItem.getName());
		recordPlayerItem.setName("Pink Record Player");
		assertEquals("Pink Record Player", recordPlayerItem.getName());
	}

	@Test
	public void getSetRetailPrice() {
		EndYearSale.StoreItem recordPlayerItem = new EndYearSale.StoreItem("Record Player", 108.50, .65);
		assertEquals(108.50, recordPlayerItem.getRetailPrice(), 0);
		recordPlayerItem.setRetailPrice(99.99);
		assertEquals(99.99, recordPlayerItem.getRetailPrice(), 0);
	}

	@Test
	public void getSetDiscount() {
		EndYearSale.StoreItem recordPlayerItem = new EndYearSale.StoreItem("Record Player", 108.50, .65);
		assertEquals(.65, recordPlayerItem.getDiscount(), 0);
		recordPlayerItem.setDiscount(.75);
		assertEquals(.75, recordPlayerItem.getDiscount(), 0);
	}

	@Test
	public void toStringy() {
		EndYearSale.StoreItem recordPlayerItem = new EndYearSale.StoreItem("Record Player", 108.50, .65);
		assertEquals("Name: Record Player, Retail price: 108.5, Discount 0.65", recordPlayerItem.toString());
	}

	@Test
	public void findLeastExpensive() {
		EndYearSale.StoreItem leastExpensive = new EndYearSale.StoreItem("Dress", 34.99, .75);

		List<EndYearSale.StoreItem> items = List.of(
				new EndYearSale.StoreItem("T-Shirt", 19.99, .4),
				leastExpensive,
				new EndYearSale.StoreItem("Record Player", 92.99, .75),
				new EndYearSale.StoreItem("Hat", 23.99, .1),
				new EndYearSale.StoreItem("Jeans", 54.99, .65));

		assertEquals(Optional.of(leastExpensive), EndYearSale.StoreItem.findLeastExpensive(items));
	}

	@Test
	public void findLeastExpensive_emptyItems() {
		List<EndYearSale.StoreItem> items = List.of();
		assertEquals(Optional.empty(), EndYearSale.StoreItem.findLeastExpensive(items));
	}

	@Test
	public void findLeastExpensive_oneItem() {
		EndYearSale.StoreItem leastExpensive = new EndYearSale.StoreItem("Hat", 23.99, .1);
		List<EndYearSale.StoreItem> items = List.of(leastExpensive);
		assertEquals(Optional.of(leastExpensive), EndYearSale.StoreItem.findLeastExpensive(items));
	}

	@Test
	public void findLeastExpensive_sameItems() {
		EndYearSale.StoreItem leastExpensive = new EndYearSale.StoreItem("T-Shirt", 19.99, .4);
		List<EndYearSale.StoreItem> items = List.of(leastExpensive, leastExpensive, leastExpensive);

		assertEquals(Optional.of(leastExpensive),
				EndYearSale.StoreItem.findLeastExpensive(items));
	}

	@Test
	public void findLeastExpensive_sameItemsDifferentDiscounts() {
		EndYearSale.StoreItem leastExpensive = new EndYearSale.StoreItem("T-Shirt", 19.99, .9);
		EndYearSale.StoreItem secondLeastExpensive = new EndYearSale.StoreItem("T-Shirt", 19.99, .7);
		EndYearSale.StoreItem thirdLeastExpensive = new EndYearSale.StoreItem("T-Shirt", 19.99, .6);

		List<EndYearSale.StoreItem> items = List.of(thirdLeastExpensive,
				secondLeastExpensive, leastExpensive);

		assertEquals(Optional.of(leastExpensive),
				EndYearSale.StoreItem.findLeastExpensive(items));
	}

}