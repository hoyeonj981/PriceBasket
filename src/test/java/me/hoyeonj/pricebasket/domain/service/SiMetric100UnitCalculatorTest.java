package me.hoyeonj.pricebasket.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import me.hoyeonj.pricebasket.domain.NotSupportedUnitException;
import me.hoyeonj.pricebasket.domain.PriceWon;
import me.hoyeonj.pricebasket.domain.ZeroAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class SiMetric100UnitCalculatorTest {

  private SiMetric100UnitCalculator calculator;

  @BeforeEach
  void setUp() {
   calculator = new SiMetric100UnitCalculator();
  }

  @DisplayName("지원하지 않는 단위일 경우 예외가 발생한다")
  @ParameterizedTest
  @ValueSource(strings = {
      "lb",
      "oz",
      "adfa",
      "",
      " ",
      ""
  })
  void throwExceptionWhenUnitIsNotSupported(String givenUnit) {
    var givenPrice = PriceWon.of(1000);
    var givenAmount = BigDecimal.valueOf(1000);

    assertThatThrownBy(() -> calculator.calculate(givenPrice, givenAmount, givenUnit))
        .isInstanceOf(NotSupportedUnitException.class);
  }

  @DisplayName("총 수량이 0일 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenTotalAmountIsZero() {
    var givenPrice = PriceWon.of(1000);
    var givenAmount = BigDecimal.ZERO;
    var givenUnit = "G";

    assertThatThrownBy(() -> calculator.calculate(givenPrice, givenAmount, givenUnit))
        .isInstanceOf(ZeroAmountException.class);
  }

  @DisplayName("주어진 가격, 양, 단위에 따라 100 단위 당 가격을 계산한다")
  @ParameterizedTest
  @CsvSource({
      "6980, 327, g, 2135",
      "10480, 1.12, kg, 936",
      "4880, 1000, g, 488",
      "5580, 980, g, 569",
      "9980, 1050, g, 950",
      "5980, 315, g, 1898",
      "9480, 840, g, 1129",
      "2680, 1.8, l, 149",
      "2880, 1.5, l, 192",
      "2580, 1.5, l, 172",
      "2880, 2130, ml, 135",
      "8180, 2580, ml, 317"
  })
  void calculate100UnitPrice(String price, String amount, String unit, String expectedPrice) {
    var givenPrice = PriceWon.of(price);
    var givenAmount = new BigDecimal(amount);
    var givenUnit = unit;
    var expected = PriceWon.of(expectedPrice);

    var actual = calculator.calculate(givenPrice, givenAmount, givenUnit);

    assertThat(actual).isEqualTo(expected);
  }
}