package me.hoyeonj.pricebasket.domain;

import java.math.BigDecimal;

@FunctionalInterface
public interface UnitPriceCalculator {
  PriceWon calculate(final PriceWon totalPrice, final BigDecimal totalAmount, final String symbol);
}
