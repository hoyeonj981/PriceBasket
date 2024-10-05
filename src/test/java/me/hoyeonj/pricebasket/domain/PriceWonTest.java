package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceWonTest {

  @DisplayName("long value를 받아 Price 객체를 생성한다")
  @Test
  void test() {
    var price = PriceWon.of(1000L);

    assertThat(price.getValue()).isEqualTo(new BigDecimal(1000L));
  }

  @DisplayName("String value를 받아 Price 객체를 생성한다")
  @Test
  void test2() {
    var price = PriceWon.of("1000");

    assertThat(price.getValue()).isEqualTo(new BigDecimal("1000"));
  }

  @DisplayName("음수를 가격으로 만들 경우 예외가 발생한다")
  @Test
  void throw_exception_when_price_is_negative_value() {
    var given = -1;

    assertThatThrownBy(() -> PriceWon.of(given))
        .isInstanceOf(NegativePriceException.class);
  }

  @DisplayName("두 Price를 더한 결과는 value들을 더한 값과 같다")
  @Test
  void result_of_adding_two_prices_is_equal_to_values_added() {
    var givenValue1 = 1000L;
    var givenValue2 = 2000L;
    var price1 = PriceWon.of(givenValue1);
    var price2 = PriceWon.of(givenValue2);
    var expected = PriceWon.of(givenValue1 + givenValue2);

    var actual = price1.add(price2);

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("두 Price를 뺸 결과는 value들을 뺀 값과 같다")
  @Test
  void result_of_subtracted_two_prices_is_equal_to_values_subtracted() {
    var givenValue1 = 3000L;
    var givenValue2 = 2000L;
    var price1 = PriceWon.of(givenValue1);
    var price2 = PriceWon.of(givenValue2);
    var expected = PriceWon.of(givenValue1 - givenValue2);

    var actual = price1.subtract(price2);

    assertThat(actual).isEqualTo(expected);
  }
}
