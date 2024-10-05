package me.hoyeonj.pricebasket.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class PriceWon {

  private final BigDecimal value;

  public static PriceWon of(final long value) {
    return new PriceWon(BigDecimal.valueOf(value));
  }

  public static PriceWon of(final String value) {
    return new PriceWon(new BigDecimal(value));
  }

  private PriceWon(final BigDecimal value) {
    checkNegative(value);
    this.value = value;
  }

  private void checkNegative(final BigDecimal price) {
    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new NegativePriceException("가격은 음수일 수 없습니다. - " + price);
    }
  }

  public PriceWon add(final PriceWon priceWon) {
    return new PriceWon(this.value.add(priceWon.value));
  }

  public PriceWon subtract(final PriceWon priceWon) {
    return new PriceWon(this.value.subtract(priceWon.value));
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
    final PriceWon priceWon = (PriceWon) o;
    return Objects.equals(value, priceWon.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
