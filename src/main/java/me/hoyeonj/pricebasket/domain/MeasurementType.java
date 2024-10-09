package me.hoyeonj.pricebasket.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum MeasurementType {

  G("g"),
  KG("kg"),
  ML("ml"),
  L("l"),
  ;

  private static final Map<String, MeasurementType> symbolMap = new HashMap<>();

  static {
    for (MeasurementType type : values()) {
      symbolMap.put(type.symbol, type);
    }
  }

  private final String symbol;

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

  MeasurementType(final String symbol) {
    this.symbol = symbol;
  }

  public String getSymbol() {
    return this.symbol;
  }
}
