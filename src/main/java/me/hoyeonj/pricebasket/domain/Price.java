package me.hoyeonj.pricebasket.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {

  private final BigDecimal value;

  public static Price of(final long value) {
    return new Price(BigDecimal.valueOf(value));
  }

  public static Price of(final String value) {
    return new Price(new BigDecimal(value));
  }

  private Price(final BigDecimal value) {
    checkNegative(value);
    this.value = value;
  }

  private void checkNegative(final BigDecimal price) {
    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new NegativePriceException("가격은 음수일 수 없습니다. - " + price);
    }
  }

  public Price add(final Price price) {
    return new Price(this.value.add(price.value));
  }

  public Price subtract(final Price price) {
    return new Price(this.value.subtract(price.value));
  }

  public BigDecimal getValue() {
    return new BigDecimal(this.value.longValue());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Price price = (Price) o;
    return Objects.equals(value, price.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
