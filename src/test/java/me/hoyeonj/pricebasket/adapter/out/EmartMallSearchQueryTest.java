package me.hoyeonj.pricebasket.adapter.out;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class EmartMallSearchQueryTest {

  @ParameterizedTest
  @MethodSource("queryParameters")
  void 쿼리파라미터가_NULL_공백_빈문자열_일_경우_예외가_발생한다(final String value) {
    assertThatThrownBy(() -> new EmartMallSearchQuery(value))
        .isInstanceOf(InvalidQueryParameterException.class);
  }

  private static Stream<String> queryParameters() {
    return Stream.of(null, "", " ");
  }
}