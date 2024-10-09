package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StandardUnitPriceCalculatorTest {

  private UnitPriceCalculator calculator;

  @BeforeEach
  void setUp() {
    calculator = new StandardUnitPriceCalculator();
  }

  @DisplayName("주어진 값을 가지고 단위 가격을 계산한다")
  @ParameterizedTest
  @CsvSource({
      "3860, 1.2, kg, 322",
      "3190, 500, g, 638",
      "1000, 1.5, L, 67",
      "5390, 3, l, 180"
  })
  void calculateUnitPriceWithGivenValues(String totalPrice, BigDecimal totalAmount,
      String unitSymbol, String expectedPrice) {
    var expected = PriceWon.of(expectedPrice);

    var actual = calculator.calculate(PriceWon.of(totalPrice), totalAmount, unitSymbol);

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("총 가격이 0일 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenTotalPriceIsZero() {
    assertThatThrownBy(() -> calculator.calculate(PriceWon.ZERO, BigDecimal.ONE, "G"))
        .isInstanceOf(ZeroTotalPriceException.class);
  }

  @DisplayName("총 무게가 0일 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenAmountIsZero() {
    assertThatThrownBy(() -> calculator.calculate(PriceWon.of(1000), BigDecimal.ZERO, "G"))
        .isInstanceOf(ZeroAmountException.class);
  }
}
