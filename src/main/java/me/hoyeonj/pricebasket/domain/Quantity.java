package me.hoyeonj.pricebasket.domain;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Quantity {

  private static final int ONE = 1;

  private final AtomicInteger value;

  public Quantity() {
    this(0);
  }

  public Quantity(final int value) {
    validateNegativeQuantity(value);
    this.value = new AtomicInteger(value);
  }

  private void validateNegativeQuantity(final int value) {
    if (value < 0) {
      throw new NegativeQuantityException("총 수량은 음수일 수 없습니다. - " + value);
    }
  }

  public int increaseQuantity(final int amount) {
    validateNegativeAmount(amount);
    return this.value.updateAndGet(current -> current + amount);
  }

  private void validateNegativeAmount(final int amount) {
    if (amount < 0) {
      throw new NegativeValueException("수량 연산에 음수를 사용할 수 없습니다. - " + amount);
    }
  }

  public int increaseOne() {
    return this.increaseQuantity(ONE);
  }

  public int decreaseQuantity(final int amount) {
    validateNegativeAmount(amount);
    return this.value.updateAndGet(current -> {
      final var result = current - amount;
      validateNegativeResult(result);
      return result;
    });
  }

  private void validateNegativeResult(final int result) {
    if (result < 0) {
      throw new NegativeResultException("수량 연산의 결과값이 음수일 수 없습니다. - " + result);
    }
  }

  public int decreaseOne() {
    return this.decreaseQuantity(ONE);
  }

  public int getValue() {
    return this.value.intValue();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Quantity quantity = (Quantity) o;
    return Objects.equals(value, quantity.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
