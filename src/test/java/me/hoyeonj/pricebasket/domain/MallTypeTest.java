package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MallTypeTest {

  @DisplayName("MallType이 존재하지 않는다면 예외가 발생한다")
  @ParameterizedTest
  @ValueSource(strings = {
      "LotteMart",
      "Costco",
      "Amazon",
      "BestBuy",
      "Coupang"
  })
  void throwExceptionWhenMallTypeDoesNotExist(String given) {
    assertThatThrownBy(() -> MallType.from(given))
        .isInstanceOf(NotSupportedMallException.class);
  }

  @DisplayName("MartName이 null이라면 예외가 발생한다")
  @Test
  void throwExceptionWhenMartNameIsNull() {
    String given = null;
    assertThatThrownBy(() -> MallType.from(given))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
