package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class UnitPriceTest {

  @DisplayName("주어진 양, 단위, 총가격, 100 단위가격을 올바르게 계산한다")
  @ParameterizedTest
  @CsvSource({
      "100, g, 10_000, 10_000",
      "2, kg, 10_000, 500",
      "500, ml, 10_000, 2_000",
      "5, l, 10_000, 200",
      "2.5, kg, 24_900, 996",
  })
  void create_should_calculate_correct_unitPrice(
      BigDecimal amount, String givenSymbol, long total, long expectedUnitPrice) {
    var totalPrice = PriceWon.of(total);
    var expectedPrice = PriceWon.of(expectedUnitPrice);
    var expectedSymbol = "100" + givenSymbol;

    var actual = UnitPrice.create(amount, totalPrice, givenSymbol);
    var symbolUnit = actual.getUnit();

    assertThat(actual.getPriceWon()).isEqualTo(expectedPrice);
    assertThat(symbolUnit).isEqualTo(expectedSymbol);
  }

  @DisplayName("지원하지 않는 단위 표기는 예외가 발생한다")
  @ParameterizedTest
  @MethodSource("invalidSymbols")
  void throw_exception_when_unit_symbol_is_not_supported(String givenSymbol) {
    var amount = new BigDecimal("100");
    var totalPrice = PriceWon.of("100");

    assertThatThrownBy(() -> UnitPrice.create(amount, totalPrice, givenSymbol))
        .isInstanceOf(NotSupportedUnitException.class);
  }

  private static Stream<String> invalidSymbols() {
    return Stream.of(
        null, "", " ",
        "oz", "lb", "t", "gz"
    );
  }

  @DisplayName("양이 0일 경우 예외가 발생한다")
  @Test
  void throw_exception_when_zero_amount() {
    PriceWon total = PriceWon.of(10_000L);
    assertThatThrownBy(() -> UnitPrice.create(BigDecimal.ZERO, total, "g"))
        .isInstanceOf(ZeroAmountException.class);
  }

  @DisplayName("총가격이 0일 경우 예외가 발생한다")
  @Test
  void throw_exception_when_total_price_is_zero() {
    PriceWon total = PriceWon.of(BigDecimal.ZERO);
   assertThatThrownBy(() -> UnitPrice.create(BigDecimal.TEN, total, "g"))
       .isInstanceOf(ZeroTotalPriceException.class);
  }
}
