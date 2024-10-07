package me.hoyeonj.pricebasket.domain;

import static me.hoyeonj.pricebasket.domain.MeasurementType.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class MeasurementTypeTest {

  @DisplayName("단위 Symbol이 존재하지 않는다면 예외가 발생한다")
  @ParameterizedTest
  @ValueSource(strings = {
      "", " ",
      "oz", "lb", "t", "gz"
  })
  void throw_exception_when_symbol_is_not_exist(String given) {
    assertThatThrownBy(() -> from(given))
        .isInstanceOf(NotSupportedUnitException.class);
  }

  @DisplayName("단위 Symbol이 null이라면 예외가 발생한다")
  @Test
  void throw_exception_when_symbol_is_null() {
    String given = null;
    assertThatThrownBy(() -> from(given))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("주어진 양을 기본 미터법으로 변환한다")
  @ParameterizedTest
  @CsvSource({
      "l, 1.5, 1500",
      "ml, 355, 355",
      "g, 1500, 1500",
      "kg, 3.9, 3900",
  })
  void convert_amount_to_basic_metric(
      String givenSymbol, String givenAmount, String givenExpected) {
    var symbol = from(givenSymbol);
    var expected = new BigDecimal(givenExpected);

    var actual = symbol.convertToBasicUnit(givenAmount);

    assertThat(actual).isEqualTo(expected);
  }
}
