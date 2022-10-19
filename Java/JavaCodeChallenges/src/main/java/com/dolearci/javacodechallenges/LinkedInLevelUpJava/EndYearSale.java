package com.dolearci.javacodechallenges.LinkedInLevelUpJava;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class EndYearSale {

	@Getter
	@Setter
	@AllArgsConstructor
	public static class StoreItem {
		String name;
		double retailPrice;
		double discount;

		public static Optional<StoreItem> findLeastExpensive(Collection<StoreItem> items) {
			double minPrice = Double.MAX_VALUE;
			Optional<StoreItem> cheapItem = Optional.empty();
			for (StoreItem item : items) {
				if (item.getRetailPrice() * (1 - item.getDiscount()) < minPrice) {
					minPrice = item.getRetailPrice() * (1 - item.getDiscount());
					cheapItem = Optional.of(item);
				}
			}
			return cheapItem;
		}

		@Override
		public String toString() {
			return "Name: " + name + ", " + "Retail price: " + retailPrice + ", " + "Discount " + discount;
		}

		public void main(String[] args) {
			List<StoreItem> items = List.of(
					new StoreItem("T-Shirt", 19.99, .4),
					new StoreItem("Dress", 34.99, .75),
					new StoreItem("Record Player", 92.99, .75),
					new StoreItem("Hat", 23.99, .1),
					new StoreItem("Jeans", 54.99, .65));

			Optional<StoreItem> leastExpensiveOpt = StoreItem.findLeastExpensive(items);
			leastExpensiveOpt.ifPresent(storeItem -> System.out.println("The least expensive item is " +
					storeItem));
		}
	}
}
