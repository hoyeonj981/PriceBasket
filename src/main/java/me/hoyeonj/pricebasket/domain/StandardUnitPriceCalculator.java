package me.hoyeonj.pricebasket.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class StandardUnitPriceCalculator implements UnitPriceCalculator {

  private static final BigDecimal ONE_HUNDRED = new BigDecimal(100L);
  private static final int PRECISION = 10;
  private static final int FIRST_NUMBER = 0;

  private final Map<MeasurementType, Function<BigDecimal, BigDecimal>> conversionMap;

  public StandardUnitPriceCalculator() {
    conversionMap = new EnumMap<>(MeasurementType.class);
    conversionMap.put(MeasurementType.G, (amount) -> amount);
    conversionMap.put(MeasurementType.KG, (amount) -> amount.multiply(BigDecimal.valueOf(1000)));
    conversionMap.put(MeasurementType.ML, (amount) -> amount);
    conversionMap.put(MeasurementType.L, (amount) -> amount.multiply(BigDecimal.valueOf(1000)));
  }

  /*
   * g, ml : (unit price) = 100 / (amout) * (total price)
   */
  @Override
  public PriceWon calculate(final PriceWon totalPrice, final BigDecimal totalAmount,
      final String symbol) {
    checkZeroPrice(totalPrice);
    final var unit = MeasurementType.from(symbol);
    final var amount = convertToBasicUnit(totalAmount, unit);
    checkZeroAmount(amount);
    final var unitPrice = calculateInBasicMetric(totalPrice.getValue(), amount);
    return PriceWon.of(unitPrice);
  }

  private BigDecimal convertToBasicUnit(final BigDecimal totalAmount, final MeasurementType unit) {
    return conversionMap.get(unit).apply(totalAmount);
  }

  private void checkZeroPrice(final PriceWon totalPrice) {
    if (totalPrice.compareTo(PriceWon.ZERO) == 0) {
      throw new ZeroTotalPriceException("totalPrice는 0일 수 없습니다");
    }
  }

  private void checkZeroAmount(final BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) == 0) {
      throw new ZeroAmountException("amount는 0일 수 없습니다");
    }
  }

  private BigDecimal calculateInBasicMetric(final BigDecimal totalPrice, final BigDecimal amount) {
    final var divided = ONE_HUNDRED.divide(amount, PRECISION, RoundingMode.HALF_DOWN);
    return totalPrice.multiply(divided)
        .setScale(FIRST_NUMBER, RoundingMode.UP);
  }
}
