package me.hoyeonj.pricebasket.domain;

import static me.hoyeonj.pricebasket.domain.MeasurementType.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class MeasurementTypeTest {

  @DisplayName("단위 Symbol이 null이거나 존재하지 않는다면 예외가 발생한다")
  @ParameterizedTest
  @MethodSource("invalidSymbols")
  void throw_exception_when_symbol_is_null_or_not_exist(String given) {
    assertThatThrownBy(() -> from(given))
        .isInstanceOf(IllegalArgumentException.class);
  }

  private static Stream<String> invalidSymbols() {
    return Stream.of(
        null, "", " ",
        "oz", "lb", "t", "gz"
    );
  }

  @DisplayName("단위당 가격은 100을 기준으로 계산된다")
  @ParameterizedTest
  @CsvSource({
      "1.8, l, 2680, 149",
      "3.6, L, 5360, 149",
      "1, KG, 3780, 378",
      "1, Kg, 1980, 198",
      "315, G, 5980, 1899",
      "1000, g, 4880, 488"
  })
  void unit_of_price_is_calculated_on_the_basis_100(
      String amount, String symbol, String totalPrice, String unitPrice) {
    var givenAmount = new BigDecimal(amount);
    var givenTotalPrice = PriceWon.of(totalPrice);
    var expected = PriceWon.of(unitPrice);

    var actual = from(symbol)
        .calculateStandardUnitPrice(givenTotalPrice, givenAmount);

    assertThat(actual).isEqualTo(expected);
  }
}
