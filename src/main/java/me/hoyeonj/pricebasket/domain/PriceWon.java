package me.hoyeonj.pricebasket.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class PriceWon implements Comparable<PriceWon> {

  private static final int ROUND_UP_FRIST_DECIMAL = 0;
  private static final int PRECISION = 10;
  private static final BigDecimal ONE_HUNDRED = new BigDecimal(100L);

  public static final PriceWon ZERO = new PriceWon(BigDecimal.ZERO);

  private final BigDecimal value;

  public static PriceWon of(final long value) {
    return new PriceWon(BigDecimal.valueOf(value));
  }

  public static PriceWon of(final String value) {
    return new PriceWon(new BigDecimal(value));
  }

  public static PriceWon of(final BigDecimal value) {
    return new PriceWon(value);
  }

  public PriceWon(final PriceWon priceWon) {
    this.value = priceWon.value;
  }

  private PriceWon(final BigDecimal value) {
    checkNegative(value);
    this.value = value.setScale(ROUND_UP_FRIST_DECIMAL, RoundingMode.UP);
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

  public PriceWon applyPercentage(final int percentage) {
    return applyPercentage(new BigDecimal(percentage));
  }

  public PriceWon applyPercentage(final BigDecimal percentage) {
    if (percentage.compareTo(BigDecimal.ZERO) < 0) {
      throw new NegativePercentageException("백분율은 음수일 수 없습니다. - " + percentage);
    }
    final var factor = percentage.divide(ONE_HUNDRED, PRECISION, RoundingMode.HALF_UP);
    final var result = this.value.multiply(factor)
        .setScale(ROUND_UP_FRIST_DECIMAL, RoundingMode.UP);
    return new PriceWon(result);
  }

  public PriceWon multiplyQuantity(final int quantity) {
    return new PriceWon(this.value.multiply(BigDecimal.valueOf(quantity)));
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

  @Override
  public int compareTo(final PriceWon o) {
    return this.value.compareTo(o.value);
  }

  @Override
  public String toString() {
    return "PriceWon{" +
        "value=" + value +
        '}';
  }
}
