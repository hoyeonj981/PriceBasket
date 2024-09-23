package me.hoyeonj.pricebasket.adapter.out;

import static me.hoyeonj.pricebasket.adapter.out.EmartMallUrl.*;
import static org.assertj.core.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class EmartMallUrlTest {

  @Test
  void 유효한_HTTPS_URL을_생성한다() {
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

  @ParameterizedTest
  @MethodSource("queryParameters")
  void 쿼리파라미터가_NULL_공백_빈문자열_일_경우_예외가_발생한다(final String value) {
    assertThatThrownBy(() -> HTTPS_SEARCH_URL.createSearchUrl(value))
        .isInstanceOf(InvalidQueryParameterException.class);
  }

  private static Stream<String> queryParameters() {
    return Stream.of(null, "", " ");
  }
}
