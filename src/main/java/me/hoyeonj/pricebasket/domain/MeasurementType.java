package me.hoyeonj.pricebasket.domain;

import static me.hoyeonj.pricebasket.domain.MeasurementType.StandardUnitMultipliers.G_TO_100G;
import static me.hoyeonj.pricebasket.domain.MeasurementType.StandardUnitMultipliers.KG_TO_100G;
import static me.hoyeonj.pricebasket.domain.MeasurementType.StandardUnitMultipliers.L_TO_100ML;
import static me.hoyeonj.pricebasket.domain.MeasurementType.StandardUnitMultipliers.ML_TO_100ML;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum MeasurementType {

  G("g", G_TO_100G),
  KG("kg", KG_TO_100G),
  ML("ml", ML_TO_100ML),
  L("l", L_TO_100ML)
  ;

  private static final int PRECISION = 10;
  private static final Map<String, MeasurementType> symbolMap = new HashMap<>();

  static {
    for (MeasurementType type : values()) {
      symbolMap.put(type.symbol, type);
    }
  }

  private final String symbol;
  private final BigDecimal standardMultiplier;

  public static MeasurementType from(final String symbol) {
    if (Objects.isNull(symbol)) {
      throw new IllegalArgumentException("symbol은 null일 수 없습니다.");
    }
    final var lowerSymbol = symbol.toLowerCase();
    final var measurementType = symbolMap.get(lowerSymbol);
    if (Objects.isNull(measurementType)) {
      throw new IllegalArgumentException("해당 표현 단위는 존재하지 않습니다. - " + symbol);
    }
    return measurementType;
  }

  MeasurementType(final String symbol, final BigDecimal standardMultiplier) {
    this.symbol = symbol;
    this.standardMultiplier = standardMultiplier;
  }

  /*
  * g, ml : (unit price) = 100 / (amout) * (total price)
  * kg, l : (unit price) = 0.1 / (amount) * (total price)
   */
  public PriceWon calculateStandardUnitPrice(final PriceWon totalPrice, final BigDecimal amount) {
    final BigDecimal divided = standardMultiplier.divide(amount, PRECISION, RoundingMode.HALF_DOWN);
    final var value = totalPrice.getValue();
    final var multiplied = divided.multiply(value);
    return PriceWon.of(multiplied);
  }

  public String getSymbol() {
    return "100" + this.symbol;
  }

  static class StandardUnitMultipliers {
    static BigDecimal ML_TO_100ML = new BigDecimal("100");
    static BigDecimal L_TO_100ML = new BigDecimal("0.1");
    static BigDecimal G_TO_100G = new BigDecimal("100");
    static BigDecimal KG_TO_100G = new BigDecimal("0.1");
  }
}
