package me.hoyeonj.pricebasket.domain;

import java.util.Objects;
import java.util.UUID;

public class BasketId {

  private final String value;

  static BasketId create() {
    return new BasketId(UUID.randomUUID().toString());
  }

  public static BasketId from(final String id) {
    return new BasketId(id);
  }

  private BasketId(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(final Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final BasketId basketId = (BasketId) o;
    return Objects.equals(value, basketId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
