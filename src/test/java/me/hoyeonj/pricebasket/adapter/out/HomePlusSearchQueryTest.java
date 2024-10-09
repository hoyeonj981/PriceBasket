package me.hoyeonj.pricebasket.adapter.out;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class HomePlusSearchQueryTest {

  @DisplayName("쿼리파라미터가 NULL, 공백, 빈문자열 일 경우 예외가 발생한다")
  @ParameterizedTest
  @MethodSource("queryParameters")
  void throwExceptionWhenQueryParameterIsNullOrEmptyOrBlank(final String value) {
    assertThatThrownBy(() -> new HomePlusSearchQuery(value))
        .isInstanceOf(InvalidQueryParameterException.class);
  }

  private static Stream<String> queryParameters() {
    return Stream.of(null, "", " ");
  }
}