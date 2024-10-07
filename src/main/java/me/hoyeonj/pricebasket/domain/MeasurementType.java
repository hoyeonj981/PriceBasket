package me.hoyeonj.pricebasket.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public enum MeasurementType {

  G("g", BigDecimal::new),
  KG("kg", MeasurementType::getDecimal),
  ML("ml", BigDecimal::new),
  L("l", MeasurementType::getDecimal),
  ;

  private static BigDecimal getDecimal(final String amount) {
    return new BigDecimal(amount)
        .multiply(BigDecimal.valueOf(1000L))
        .setScale(0, RoundingMode.HALF_UP);
  }

  private static final Map<String, MeasurementType> symbolMap = new HashMap<>();

  static {
    for (MeasurementType type : values()) {
      symbolMap.put(type.symbol, type);
    }
  }

  private final String symbol;
  private final Function<String, BigDecimal> metricUnitConverter;

  public static MeasurementType from(final String symbol) {
    if (Objects.isNull(symbol)) {
      throw new IllegalArgumentException("symbol은 null일 수 없습니다.");
    }
    final var lowerSymbol = symbol.toLowerCase();
    final var measurementType = symbolMap.get(lowerSymbol);
    if (Objects.isNull(measurementType)) {
      throw new NotSupportedUnitException("해당 표현 단위는 존재하지 않습니다. - " + symbol);
    }
    return measurementType;
  }

  MeasurementType(final String symbol, final Function<String, BigDecimal> metricUnitConverter) {
    this.symbol = symbol;
    this.metricUnitConverter = metricUnitConverter;
  }

  public BigDecimal convertToBasicUnit(final String amount) {
    return symbolMap.get(this.symbol)
        .metricUnitConverter.apply(amount);
  }

  public String getSymbol() {
    return this.symbol;
  }
}
