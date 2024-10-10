package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuantityTest {

  @DisplayName("수량을 더할 때 음수를 전달하면 예외가 발생한다")
  @Test
  void throwExceptionWhenIncreasingValueIsNegative() {
    var quantity = new Quantity();
    var givenAmount = -1;

    assertThatThrownBy(() -> quantity.increaseQuantity(givenAmount))
        .isInstanceOf(NegativeValueException.class);
  }

  @DisplayName("수량을 뺄 때 음수를 전달하면 예외가 발생한다")
  @Test
  void throwExceptionWhenDecreasingValueIsNegative() {
    var quantity = new Quantity();
    var givenAmount = -1;

    assertThatThrownBy(() -> quantity.decreaseQuantity(givenAmount))
        .isInstanceOf(NegativeValueException.class);
  }

  @DisplayName("총 수량이 음수일 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenQuantityIsNegative() {
    var givenAmount = -1;

    assertThatThrownBy(() -> new Quantity(givenAmount))
        .isInstanceOf(NegativeQuantityException.class);
  }

  @DisplayName("수량을 빼는 연산 시 결과 값이 음수일 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenResultOfAddingIsNegative() {
    var quantity = new Quantity(0);

    assertThatThrownBy(() -> quantity.decreaseQuantity(1))
        .isInstanceOf(NegativeResultException.class);
  }

  @DisplayName("기존 수량에 1을 더한다")
  @Test
  void addOneCurrentQuantity() {
    var given = 1;
    var quantity = new Quantity(given);
    var expected = 2;

    var actual = quantity.increaseOne();

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("기존 수량에 특정 값을 더한다")
  @Test
  void addAmountCurrentQuantity() {
    var given = 1;
    var amount = 10;
    var quantity = new Quantity(given);
    var expected = given + amount;

    var actual = quantity.increaseQuantity(amount);

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("기존 수량에 1을 뺀다")
  @Test
  void subtractOneCurrentQuantity() {
    var given = 1;
    var quantity = new Quantity(given);
    var expected = 0;

    var actual = quantity.decreaseOne();

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("기존 수량에 특정 값을 뺀다")
  @Test
  void subtractAmountCurrentQuantity() {
    var given = 10;
    var amount = 1;
    var quantity = new Quantity(given);
    var expected = given - amount;

    var actual = quantity.decreaseQuantity(amount);

    assertThat(actual).isEqualTo(expected);
  }
}
