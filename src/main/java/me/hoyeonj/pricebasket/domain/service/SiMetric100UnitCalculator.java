package me.hoyeonj.pricebasket.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import me.hoyeonj.pricebasket.domain.MeasurementType;
import me.hoyeonj.pricebasket.domain.NotSupportedUnitException;
import me.hoyeonj.pricebasket.domain.PriceWon;
import me.hoyeonj.pricebasket.domain.UnitPriceCalculator;
import me.hoyeonj.pricebasket.domain.ZeroAmountException;

public class SiMetric100UnitCalculator implements UnitPriceCalculator {

  private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
  private static final BigDecimal THOUSAND = BigDecimal.valueOf(1000);

  @Override
  public PriceWon calculate(final PriceWon totalPrice, final BigDecimal totalAmount,
      final String symbol) {
    if (totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new ZeroAmountException("총 수량이 0보다 커야 합니다.");
    }
    final var unit = MeasurementType.from(symbol);

    return switch (unit) {
      case G, ML -> calculatePricePer100Units(totalPrice, totalAmount);
      case KG -> calculatePricePer100GramsFromKg(totalPrice, totalAmount);
      case L -> calculatePricePer100MlFromLiter(totalPrice, totalAmount);
      default -> throw new NotSupportedUnitException("지원하지 않는 단위입니다.");
    };
  }

  private PriceWon calculatePricePer100MlFromLiter(final PriceWon totalPrice,
      final BigDecimal totalAmount) {
    final var totalMilliLiter = totalAmount.multiply(THOUSAND);
    return calculatePricePer100Units(totalPrice, totalMilliLiter);
  }

  private PriceWon calculatePricePer100GramsFromKg(final PriceWon totalPrice,
      final BigDecimal totalAmount) {
    final var totalGrams = totalAmount.multiply(THOUSAND);
    return calculatePricePer100Units(totalPrice, totalGrams);
  }

  private PriceWon calculatePricePer100Units(final PriceWon totalPrice,
      final BigDecimal totalAmount) {
    return PriceWon.of(
        totalPrice.getValue()
            .multiply(HUNDRED)
            .divide(totalAmount, 0, RoundingMode.HALF_UP));
  }
}
