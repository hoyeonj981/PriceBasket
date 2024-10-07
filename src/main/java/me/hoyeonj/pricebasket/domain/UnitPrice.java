package me.hoyeonj.pricebasket.domain;

import java.math.BigDecimal;

public class UnitPrice {

  private final MeasurementType unit;
  private final PriceWon priceWon;

  public static UnitPrice create(final BigDecimal amount, final PriceWon totalPrice,
      final String unitSymbol) {
    checkZeroAmount(amount);
    checkZeroPrice(totalPrice);
    final var unit = getMeasurementType(unitSymbol);
    final var unitOfPrice = unit.calculateStandardUnitPrice(totalPrice, amount);
    return new UnitPrice(unit, unitOfPrice);
  }

  private static void checkZeroPrice(final PriceWon priceWon) {
    if (priceWon.compareTo(PriceWon.ZERO) == 0) {
      throw new ZeroTotalPriceException("총가격은 0일 수 없습니다");
    }
  }

  private static void checkZeroAmount(final BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) == 0) {
      throw new ZeroAmountException("단위 가격에서 양은 0일 수 없습니다");
    }
  }

  private static MeasurementType getMeasurementType(final String unitSymbol) {
    MeasurementType unit;
    try {
      unit = MeasurementType.from(unitSymbol);
    } catch (IllegalArgumentException e) {
      throw new NotSupportedUnitException(e.getMessage());
    }
    return unit;
  }

  private UnitPrice(final MeasurementType unit, final PriceWon priceWon) {
    this.unit = unit;
    this.priceWon = priceWon;
  }

  public PriceWon getPriceWon() {
    return PriceWon.of(priceWon.getValue());
  }

  public String getUnit() {
    return unit.getSymbol();
  }
}
