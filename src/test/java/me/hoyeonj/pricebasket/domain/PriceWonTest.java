package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PriceWonTest {

  @DisplayName("long value를 받아 Price 객체를 생성한다")
  @Test
  void createPriceObjectWithGivenLongValue() {
    var price = PriceWon.of(1000L);

    assertThat(price.getValue()).isEqualTo(new BigDecimal(1000L));
  }

  @DisplayName("String value를 받아 Price 객체를 생성한다")
  @Test
  void createPriceObjectWithGivenStringValue() {
    var price = PriceWon.of("1000");

    assertThat(price.getValue()).isEqualTo(new BigDecimal("1000"));
  }

  @DisplayName("음수를 가격으로 만들 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenPriceIsNegativeValue() {
    var given = -1;

    assertThatThrownBy(() -> PriceWon.of(given))
        .isInstanceOf(NegativePriceException.class);
  }

  @DisplayName("두 Price를 더한 결과는 value들을 더한 값과 같다")
  @Test
  void resultOfAddingTwoPricesIsEqualToValuesAdded() {
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
  void resultOfSubtractedTwoPricesIsEqualToValuesSubtracted() {
    var givenValue1 = 3000L;
    var givenValue2 = 2000L;
    var price1 = PriceWon.of(givenValue1);
    var price2 = PriceWon.of(givenValue2);
    var expected = PriceWon.of(givenValue1 - givenValue2);

    var actual = price1.subtract(price2);

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("원화는 소수점 첫째자리부터 무조건 올림으로 처리한다")
  @ParameterizedTest
  @CsvSource({
      "1000.0, 1000",
      "123.123, 124",
      "123.456999999, 124",
      "123.999999, 124",
      "123.11111111, 124"
  })
  void priceWonIsAlwaysRoundUpFirstDecimal(String decimalPoint, String roundUp) {
    var expected = PriceWon.of(roundUp);

    var actual = PriceWon.of(decimalPoint);

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("PriceWon 객체끼리 비교한다. 작을 경우 -1, 같다면 0, 클 경우 1")
  @ParameterizedTest
  @CsvSource({
      "10, 3, 1",
      "3, 10, -1",
      "10, 10, 0",
  })
  void priceIsComparableEachOne(long left, long right, int expected) {
    var leftOperand = PriceWon.of(left);
    var rightOperand = PriceWon.of(right);

    var actual = leftOperand.compareTo(rightOperand);

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("현재 가격에 주어진 백분율을 적용한다")
  @ParameterizedTest
  @CsvSource({
      "1000, 10, 100",
      "3000, 25, 750",
      "4990, 20, 998",
      "7980, 25, 1995",
      "8480, 23, 1951"
  })
  void applyGivenPercentageToPrice(String price, int percentage, String applied) {
    var givenPrice = PriceWon.of(price);
    var expected = PriceWon.of(applied);

    var actual = givenPrice.applyPercentage(percentage);

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("백분율이 음수일 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenPercentageIsNegative() {
    var price = PriceWon.of(100L);
    var givenPercentage = -1;

    assertThatThrownBy(() -> price.applyPercentage(givenPercentage))
        .isInstanceOf(NegativePercentageException.class);
  }

  @DisplayName("주어진 양만큼 곱한 price 객체를 만든다")
  @Test
  void createPriceObjectWithGivenQuantity() {
    var givenQuantity = 15;
    var givenPrice = 4990L;
    var expected = PriceWon.of(givenPrice * givenQuantity);

    var actual = PriceWon.of(givenPrice).multiplyQuantity(givenQuantity);

    assertThat(actual).isEqualTo(expected);
  }
}
