package me.hoyeonj.pricebasket.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class UnitPrice implements Comparable<UnitPrice> {

  private final MeasurementType unit;
  private final PriceWon priceWon;
  private final BigDecimal totalAmount;
  private final UnitPriceCalculator calculator;

  public static UnitPrice create(final BigDecimal totalAmount, final PriceWon totalPrice,
      final String unitSymbol, final UnitPriceCalculator calculator) {
    final var unitPrice = calculator.calculate(totalPrice, totalAmount, unitSymbol);
    return new UnitPrice(
        MeasurementType.from(unitSymbol),
        unitPrice,
        totalAmount,
        calculator
    );
  }

  private UnitPrice(final MeasurementType unit, final PriceWon priceWon,
      final BigDecimal totalAmount, final UnitPriceCalculator calculator) {
    this.unit = unit;
    this.priceWon = priceWon;
    this.totalAmount = totalAmount;
    this.calculator = calculator;
  }

  public UnitPrice updateUnitPrice(final PriceWon totalPrice) {
    return new UnitPrice(
        this.unit,
        calculator.calculate(totalPrice, this.totalAmount, this.unit.getSymbol()),
        this.totalAmount,
        this.calculator
    );
  }

  public PriceWon getPriceWon() {
    return new PriceWon(priceWon);
  }

  public MeasurementType getUnit() {
    return this.unit;
  }

  public BigDecimal getTotalAmount() {
    return BigDecimal.valueOf(this.totalAmount.longValue());
  }

  @Override
  public int compareTo(final UnitPrice other) {
    return this.priceWon.compareTo(other.priceWon);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final UnitPrice unitPrice = (UnitPrice) o;
    return unit == unitPrice.unit && Objects.equals(priceWon, unitPrice.priceWon)
        && Objects.equals(totalAmount, unitPrice.totalAmount) && Objects.equals(
        calculator, unitPrice.calculator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unit, priceWon, totalAmount, calculator);
  }
}
