package me.hoyeonj.pricebasket.domain;

import static me.hoyeonj.pricebasket.domain.MeasurementType.from;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
}
