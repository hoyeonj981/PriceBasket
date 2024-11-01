package me.hoyeonj.pricebasket.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum MallType {

  EMARTMALL("emart"),
  HOMEPLUS("homeplus");

  private static final Map<String, MallType> mallTypeMap = new HashMap<>();

  static {
    for (MallType type : values()) {
      mallTypeMap.put(type.name, type);
    }
  }

  public static MallType from(final String martName) {
    if (Objects.isNull(martName)) {
      throw new IllegalArgumentException("martName은 null일 수 없습니다.");
    }
    final var lowerName = martName.toLowerCase();
    final var mallType = mallTypeMap.get(lowerName);
    if(Objects.isNull(mallType)) {
      throw new NotSupportedMallException("해당 마트는 지원하지 않습니다. - " + mallType);
    }
    return mallType;
  }

  private final String name;

  MallType(final String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
