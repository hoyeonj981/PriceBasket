package me.hoyeonj.pricebasket.adapter.out;

import static me.hoyeonj.pricebasket.adapter.out.EmartMallUrl.HTTPS_SEARCH_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class EmartMallUrlTest {

  @DisplayName("유효한 HTTPS URL을 생성한다")
  @Test
  void create_valid_https_url() {
    var testKeyword = "testKeyword";
    var searchUrl = HTTPS_SEARCH_URL.createSearchUrl(testKeyword);

    var actual = isValidUrl(searchUrl);

    assertThat(actual).isTrue();
  }
  
  private boolean isValidUrl(final String urlString) {
    try {
      new URL(urlString);
      return true;
    } catch (MalformedURLException e) {
      return false;
    }
  }

  @DisplayName("쿼리파라미터가 NULL, 공백, 빈문자열 일 경우 예외가 발생한다")
  @ParameterizedTest
  @MethodSource("queryParameters")
  void throw_exception_when_query_parameter_is_null_or_empty_blank(final String value) {
    assertThatThrownBy(() -> HTTPS_SEARCH_URL.createSearchUrl(value))
        .isInstanceOf(InvalidQueryParameterException.class);
  }

  private static Stream<String> queryParameters() {
    return Stream.of(null, "", " ");
  }
}
